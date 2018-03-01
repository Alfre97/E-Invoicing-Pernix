package com.pernix.service.hacienda.invoicing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.KeyStore;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.production.DataObjectReference;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesBesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.production.XadesTSigningProfile;
import xades4j.properties.DataObjectDesc;
import xades4j.providers.CertificateValidationProvider;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.impl.AuthenticatedTimeStampTokenProvider;
import xades4j.providers.impl.DefaultMessageDigestProvider;
import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;
import xades4j.providers.impl.PKIXCertificateValidationProvider;
import xades4j.providers.impl.TSAHttpAuthenticationData;
import xades4j.utils.DOMHelper;
import xades4j.utils.FileSystemDirectoryCertStore;
import xades4j.verification.XadesVerificationProfile;

import org.springframework.stereotype.Service;

@Service
public class InvoiceXMLEncrypt implements InvoiceEncryptService {
	
	private static final String CERT_FOLDER = "C:/Certs/";
    private static final String CERT        = "mycert.pfx";
    private static final String KEY_STORE   = "KeyStorage";
    private static final String PASS        = "Password"; //the same in cert and keystorage

    private static final String TSA_URL     = "http://XXX.XXX.XXX/ts.inx";
    private static final String TSA_USER    = "XXXXXXXX";
    private static final String TSA_PASS    = "XXXXXXXX";

    private static final String UNSIGNED    = "C:/Test/sign-verify/unsigned.xml";
    private static final String SIGNED      = "C:/Test/sign-verify/signed-bes.xml";
    private static final String SIGNEDT     = "C:/Test/sign-verify/signed-t-bes.xml";    
    private static final String VERIFY      = "C:/Test/sign-verify/verify-bes.txt";
    private static final String VERIFYT     = "C:/Test/sign-verify/verify-t-bes.txt";
    private static final String DOCUMENT    = "C:/Test/sign-verify/document.xml";
    private static final String DOCSIGNED   = "C:/Test/sign-verify/signed.bes.xml";
    
    //Metodos sin adaptar aun, para mas info:
    //https://gist.github.com/JohnnyJosep/29cd545db3d0b7abd23279b56d4db194
    //http://luisgoncalves.github.io/xades4j/javadocs/1.3.1/reference/xades4j/package-summary.html
	
    @Override
	public String sign(String xml) throws Exception { 
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(DOCUMENT));
        Element elem = doc.getDocumentElement();
        DOMHelper.useIdAsXmlId(elem);

        KeyingDataProvider kdp = new FileSystemKeyStoreKeyingDataProvider("pkcs12",CERT_FOLDER + CERT,new FirstCertificateSelector(),new DirectPasswordProvider(PASS),
                new DirectPasswordProvider(PASS),true);
        DataObjectDesc obj = new DataObjectReference("#" + elem.getAttribute("Id")).withTransform(new EnvelopedSignatureTransform());
        SignedDataObjects dataObjs = new SignedDataObjects().withSignedDataObject(obj);

        XadesSigner signer = new XadesBesSigningProfile(kdp).newSigner();
        signer.sign(dataObjs, elem);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        DOMSource source = new DOMSource(doc);        
        StreamResult result = new StreamResult(new File(SIGNED));
        transformer.transform(source, result);
		return null;
	}
	
	@Override
	public String verifySign(String xml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new FileReader(SIGNED)));
        DOMHelper.useIdAsXmlId(doc.getDocumentElement());

        NodeList nl = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");

        FileSystemDirectoryCertStore certStore = new FileSystemDirectoryCertStore(CERT_FOLDER);
        KeyStore ks;
        try (FileInputStream fis = new FileInputStream(CERT_FOLDER + KEY_STORE)) {
            ks = KeyStore.getInstance("jks");
            ks.load(fis, PASS.toCharArray());
        }

        CertificateValidationProvider provider = new PKIXCertificateValidationProvider(
                ks, false, certStore.getStore());
        XadesVerificationProfile profile = new XadesVerificationProfile(provider);
        Element sigElem = (Element) nl.item(0);
        XAdESVerificationResult r = profile.newVerifier().verify(sigElem, null);

        System.out.println("Signature form: " + r.getSignatureForm());
        System.out.println("Algorithm URI: " + r.getSignatureAlgorithmUri());
        System.out.println("Signed objects: " + r.getSignedDataObjects().size());
        System.out.println("Qualifying properties: " + r.getQualifyingProperties().all().size());
        
        for (QualifyingProperty qp : r.getQualifyingProperties().all()) {            
            if ("SigningCertificate".equals(qp.getName())) {
                Collection<X509Certificate> certs = ((SigningCertificateProperty)qp).getsigningCertificateChain();
                certs.forEach((cert) -> {
                    System.out.println("Issuer DN: " + cert.getIssuerDN());
                });
            }
            else if ("SigningTime".equals(qp.getName())) {
                System.out.println("Time: " + ((SigningTimeProperty)qp).getSigningTime().getTime().toString());
            } else if ("SignatureTimeStamp".equals(qp.getName())) {
                System.out.println("Time stamp: " + ((SignatureTimeStampProperty)qp).getTime().toString());
            }else { 
                System.out.println("QP: " + qp.getName());
            }
        }
		return null;
	}

}
