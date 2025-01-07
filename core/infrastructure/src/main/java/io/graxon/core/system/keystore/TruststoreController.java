package io.graxon.core.system.keystore;

import io.graxon.core.system.MtlsClientProperties;
import io.graxon.library.system.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.KeyStore;
import java.security.cert.*;
import java.util.*;

/**
 *
 */
@Tag(name = "Truststore")
@RestController
@RequestMapping("/api/v1")
public class TruststoreController {
    // --------------------------------------------------------------------------------------------

    //
    private static final Logger log = LoggerFactory.getLogger(TruststoreController.class);

    //
    //
    private final MtlsClientProperties properties;

    /**
     */
    public TruststoreController(MtlsClientProperties properties) {
        this.properties = properties;
    }

    /**
     */
    @PostMapping("/certificate")
    public void addCertificate(@RequestBody String hostname) throws Exception {

        //
        final String caAlias = "ca";

        // load the keystore
        var keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        char[] keyStorePass = properties.keyStorePassword().toCharArray();
        try (InputStream is = properties.keyStore().getInputStream()) {
            keyStore.load(is, keyStorePass);
        }

        //
        keyStore.aliases().asIterator().forEachRemaining(alias -> {
            log.info("alias: {}", alias);
        });

        //
        if (!keyStore.isKeyEntry(caAlias)) {
            throw new EntityNotFoundException("CA alias not found in truststore");
        }

        //
        KeyStore.PrivateKeyEntry caEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(caAlias, new KeyStore.PasswordProtection(keyStorePass));
        if (caEntry.getCertificate() instanceof X509Certificate x509Certificate) {

            // create a new certificate
            var certificate = new CertificateBuilder()
                .setDNS(hostname)
                .setIssuer(x509Certificate)
                .setPublicKey(caEntry.getCertificate().getPublicKey())
                .setPrivateKey(caEntry.getPrivateKey())
                .build();

            // load the keystore
            var trustStore = getTrustStore();
            char[] trustStorePass = properties.trustStorePassword().toCharArray();

            // add the certificate to the truststore
            trustStore.setCertificateEntry(hostname, certificate);

            //
            trustStore.store(new FileOutputStream(properties.trustStore()), trustStorePass);
        } else {
            throw new EntityNotFoundException("Certificate not a X509Certificate");
        }

    }

    /**
     */
    private KeyStore getTrustStore() throws Exception {
        var trustStoreFile = new File(properties.trustStore());
        var trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        if (trustStoreFile.exists()) {
            log.trace("loading truststore from file: {}", trustStoreFile);
            try (InputStream is = new FileInputStream(trustStoreFile)) {
                trustStore.load(is, properties.trustStorePassword().toCharArray());
            }
        } else {
            log.trace("creating truststore file: {}", trustStoreFile);
            try(OutputStream os = new FileOutputStream(trustStoreFile)) {
                trustStore.load(null, null);
                trustStore.store(os, properties.trustStorePassword().toCharArray());
            }
        }
        return trustStore;
    }

    /**
     *
     */
    @GetMapping("/truststore")
    public Map<String, String> getKeystore() throws Exception {
        var result = new HashMap<String, String>();

        // load the keystore
        var trustStore = getTrustStore();

        //
        Enumeration<String> enumeration = trustStore.aliases();
        while (enumeration.hasMoreElements()) {
            String alias = enumeration.nextElement();
            X509Certificate certificate = (X509Certificate) trustStore.getCertificate(alias);
            result.put(alias, certificate.getSubjectX500Principal().toString());
        }
        return result;
    }

    /**
     */
    @GetMapping("/truststore/{alias}")
    public String getCertificate(@PathVariable("alias") String alias) throws Exception {

        // load the keystore
        var trustStore = getTrustStore();

        //
        if (!trustStore.containsAlias(alias)) {
            throw new EntityNotFoundException("Alias not found in truststore");
        }

        //
        Certificate certificate = trustStore.getCertificate(alias);
        return "-----BEGIN PRIVATE KEY-----" + "\n" +
            Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(certificate.getEncoded()) + "\n" +
            "-----END PRIVATE KEY-----";
    }

}
