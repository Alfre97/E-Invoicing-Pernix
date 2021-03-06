package com.pernix.einvoicing.service.hacienda.signing;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyStoreException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xades4j.production.DataObjectReference;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesEpesSigningProfile;
import xades4j.production.XadesSignatureResult;
import xades4j.production.XadesSigner;
import xades4j.properties.AllDataObjsCommitmentTypeProperty;
import xades4j.properties.DataObjectDesc;
import xades4j.properties.DataObjectTransform;
import xades4j.properties.IdentifierType;
import xades4j.properties.ObjectIdentifier;
import xades4j.properties.SignaturePolicyBase;
import xades4j.properties.SignaturePolicyIdentifierProperty;
import xades4j.providers.BasicSignatureOptionsProvider;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SignaturePolicyInfoProvider;
import xades4j.providers.SigningCertChainException;
import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;
import xades4j.verification.UnexpectedJCAException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@SuppressWarnings("deprecation")
public class InvoiceSigner {
	
	@Value("${signaturePolicy}")
	private String signaturePolicy;
	
	public String sign(String certificatePath, String certificatePin, String xml) {
		try {
			XadesSigner signer = getSigner(certificatePin, certificatePath);
			signWithoutIDEnveloped(xml, signer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

	private XadesSigner getSigner(String certificatePin, String certificatePath) throws Exception {
		KeyingDataProvider keyingProvider = getKeyingDataProvider(certificatePath, certificatePin);
		SignaturePolicyInfoProvider policyInfoProvider = new SignaturePolicyInfoProvider() {
			public SignaturePolicyBase getSignaturePolicy() {
				return new SignaturePolicyIdentifierProperty(new ObjectIdentifier(
						signaturePolicy,
						IdentifierType.URI), "".getBytes());
			}
		};
		BasicSignatureOptionsProvider basicSignatureOptionsProvider = new BasicSignatureOptionsProvider() {
			public boolean includePublicKey() {
				return true;
			}

			public boolean includeSigningCertificate() {
				return true;
			}

			public boolean signSigningCertificate() {
				return true;
			}
		};
		XadesEpesSigningProfile p = new XadesEpesSigningProfile(keyingProvider, policyInfoProvider);
		p.withBasicSignatureOptionsProvider(basicSignatureOptionsProvider);
		return p.newSigner();
	}

	private KeyingDataProvider getKeyingDataProvider(String certificatePath, String certificatePin)
			throws KeyStoreException, SigningCertChainException, UnexpectedJCAException {
		KeyingDataProvider keyingProvider = new FileSystemKeyStoreKeyingDataProvider("pkcs12", certificatePath,
				new FirstCertificateSelector(), new DirectPasswordProvider(certificatePin),
				new DirectPasswordProvider(certificatePin), true);
		if (keyingProvider.getSigningCertificateChain().isEmpty()) {
			throw new IllegalArgumentException(
					"No se ha podido inicializar el keystore con la ruta: " + certificatePath);
		}
		return keyingProvider;
	}

	private String signWithoutIDEnveloped(String xml, XadesSigner signer) throws Exception {
		Document doc;
		XadesSignatureResult result;
		try {
			doc = stringToDocument(xml);
			Element signatureParent = doc.getDocumentElement();
			Element elementToSign = doc.getDocumentElement();
			String refUri;
			if (elementToSign.hasAttribute("Id")) {
				refUri = '#' + elementToSign.getAttribute("Id");
			} else {
				if (elementToSign.getParentNode().getNodeType() != 9) {
					throw new IllegalArgumentException("Elemento sin el ID debe ser la raiz del documento");
				}
				refUri = "";
			}
			DataObjectDesc dataObjRef = new DataObjectReference(refUri)
					.withTransform(new DataObjectTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature"));
			result = signer.sign(new SignedDataObjects(new DataObjectDesc[] { dataObjRef })
					.withCommitmentType(AllDataObjsCommitmentTypeProperty.proofOfOrigin()), signatureParent);
			xml = convertDocumentToString(doc);
			System.out.println(result);
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return null;
		}
		return xml;
	}

	public static Document stringToDocument(final String xmlSource)
			throws SAXException, ParserConfigurationException, IOException {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		StringReader sr = new StringReader(xmlSource);
		is.setCharacterStream(sr);
		Document doc = db.parse(is);
		return doc;
	}

	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}

	private static String convertDocumentToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		String output = null;
		try {
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			output = writer.getBuffer().toString();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return output;
	}
}
