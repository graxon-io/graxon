package io.graxon.core.system.keystore;

import io.graxon.library.system.exceptions.EntityNotFoundException;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 *
 */
public class CertificateBuilder {

    private X509Certificate issuerCertificate;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String dns;
    private String subject;

    /**
     *
     */
    public CertificateBuilder setDNS(String dns) throws UnknownHostException {
        var hostname = getHostname(dns);
        this.dns = dns;
        this.subject = "O=" + hostname + ",L=Arnhem,ST=Gelderland,C=NL";
        return this;
    }

    /**
     */
    public CertificateBuilder setIssuer(X509Certificate x509Certificate) {
        issuerCertificate = x509Certificate;
        return this;
    }

    /**
     */
    public CertificateBuilder setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    /**
     */
    public CertificateBuilder setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    /**
     */
    public Certificate build() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        //
        X500Name issuer = new X500Name(issuerCertificate.getSubjectX500Principal().getName());
        X500Name subject = new X500Name(this.subject);

        //
        BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());
        Date notBefore = new Date();
        Date notAfter = new Date(notBefore.getTime() + 365 * 86400000L);

        //
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
            issuer, serial, notBefore, notAfter, subject, publicKey);

        //
        GeneralName[] subjectAltNames = new GeneralName[]{
            new GeneralName(GeneralName.dNSName, dns),
            new GeneralName(GeneralName.dNSName, "localhost")
        };
        certBuilder.addExtension(Extension.create(
            Extension.subjectAlternativeName, true, new GeneralNames(subjectAltNames)));

        //
        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption")
            .setProvider("BC").build(privateKey);

        //
        return new JcaX509CertificateConverter()
            .setProvider("BC").getCertificate(certBuilder.build(signer));
    }

    /**
     *
     */
    private String getHostname(String fqdn) throws EntityNotFoundException {
        try {
            InetAddress inetAddress = InetAddress.getByName(fqdn);
            return inetAddress.getHostName();
        } catch (UnknownHostException e) {
            throw new EntityNotFoundException("Unknown host: " + fqdn);
        }
    }
}
