//package io.graxon.core.system.clients;
//
//import io.graxon.core.system.MtlsClientProperties;
//import io.netty.handler.ssl.SslContextBuilder;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.TrustManagerFactory;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.cert.Certificate;
//import java.security.cert.X509Certificate;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.Arrays;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// *
// */
//@ExtendWith(SpringExtension.class)
//@EnableConfigurationProperties(MtlsClientProperties.class)
//@TestPropertySource(properties = {
//    "client.ssl.key-store=classpath:keystore.p12",
//    "client.ssl.key-store-password=changeit"
//})
//class MtlsClientConfigTest {
//
//    //
//    private static final Logger log = LoggerFactory.getLogger(MtlsClientConfigTest.class);
//
//    //
//    @Autowired
//    private MtlsClientProperties properties;
//
//    /**
//     *
//     */
//    @Test
//    void test() throws Exception {
//        assertNotNull(properties);
//        assertNotNull(properties.keyStore());
//        assertNotNull(properties.keyStorePassword());
//
//        //
//        var keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        keyStore.load(properties.keyStore().getInputStream(), properties.keyStorePassword().toCharArray());
//
//        //
//        var certificates = Collections.list(keyStore.aliases()).stream()
//            .map(alias -> getCertificate(keyStore, alias))
//            .map(cert -> (X509Certificate) cert)
//            .filter(this::validateCertificate)
//            .toArray(X509Certificate[]::new);
//
//        //
//        assertNotEquals(0, certificates.length);
//
//
//
//
//    }
//
//    /**
//     * @param store
//     * @param alias
//     * @return
//     */
//    private Certificate getCertificate(KeyStore store, String alias) {
//        try {
//            return store.getCertificate(alias);
//        } catch (KeyStoreException exception) {
//            throw new RuntimeException("Error reading truststore", exception);
//        }
//    }
//
//    /**
//     * @param certificate
//     * @return
//     */
//    private boolean validateCertificate(X509Certificate certificate) {
//        var certificateExpirationDate =
//            certificate.getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//        var certificateStartDate =
//            certificate.getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//        if (LocalDate.now().isAfter(certificateExpirationDate)) {
//            throw new RuntimeException("Service date expiration");
//        }
//
//        if (LocalDate.now().isBefore(certificateStartDate)) {
//            throw new RuntimeException(
//                "Service cannot be used until " + certificateStartDate.toString());
//        }
//        return true;
//    }
//}