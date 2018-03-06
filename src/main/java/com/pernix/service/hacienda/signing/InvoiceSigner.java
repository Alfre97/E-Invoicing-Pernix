package com.pernix.service.hacienda.signing;

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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xades4j.production.DataObjectReference;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesEpesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.properties.DataObjectDesc;
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

import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.utils.DOMHelper;

public class InvoiceSigner {
	public String sign(String certificatePath, String certificatePin, String xml)
	  {
	    try
	    {
	      XadesSigner signer = getSigner(certificatePin, certificatePath);
	      signWithoutIDEnveloped(xml, signer);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return xml;
	  }
	  
	  private XadesSigner getSigner(String certificatePin, String certificatePath)
	    throws Exception
	  {
	    KeyingDataProvider keyingProvider = getKeyingDataProvider(certificatePath, certificatePin);
	    SignaturePolicyInfoProvider policyInfoProvider = new SignaturePolicyInfoProvider()
	    {
	      public SignaturePolicyBase getSignaturePolicy()
	      {
	        return new SignaturePolicyIdentifierProperty(new ObjectIdentifier("https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/Resolucion%20Comprobantes%20Electronicos%20%20DGT-R-48-2016.pdf", IdentifierType.URI), "".getBytes());
	      }
	    };
	    BasicSignatureOptionsProvider basicSignatureOptionsProvider = new BasicSignatureOptionsProvider()
	    {
	      public boolean includePublicKey()
	      {
	        return true;
	      }
	      
	      public boolean includeSigningCertificate()
	      {
	        return true;
	      }
	      
	      public boolean signSigningCertificate()
	      {
	        return true;
	      }
	    };
	    XadesEpesSigningProfile p = new XadesEpesSigningProfile(keyingProvider, policyInfoProvider);
	    p.withBasicSignatureOptionsProvider(basicSignatureOptionsProvider);
	    return p.newSigner();
	  }
	  
	  private KeyingDataProvider getKeyingDataProvider(String certificatePath, String certificatePin)
	    throws KeyStoreException, SigningCertChainException, UnexpectedJCAException
	  {
	    KeyingDataProvider keyingProvider = new FileSystemKeyStoreKeyingDataProvider("pkcs12", certificatePath, new FirstCertificateSelector(), new DirectPasswordProvider(certificatePin), new DirectPasswordProvider(certificatePin), true);
	    if (keyingProvider.getSigningCertificateChain().isEmpty()) {
	      throw new IllegalArgumentException("No se ha podido inicializar el keystore con la ruta: " + certificatePath);
	    }
	    return keyingProvider;
	  }
	  
	  private String signWithoutIDEnveloped(String xml, XadesSigner signer)
	    throws Exception
	  {
		  Document doc;
		  doc = stringToDocument(xml);
	      Element elem = doc.getDocumentElement();
	      DOMHelper.useIdAsXmlId(elem);
		  DataObjectDesc obj = new DataObjectReference("#" + elem.getAttribute("Id"))
                .withTransform(new EnvelopedSignatureTransform());
	      SignedDataObjects dataObjs = new SignedDataObjects().withSignedDataObject(obj);
	      signer.sign(dataObjs, elem);
	      xml = convertDocumentToString(doc);
	    return xml;
	  }
	  
	  public static Document stringToDocument(final String xmlSource)   
			    throws SAXException, ParserConfigurationException, IOException {  
		  DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		  InputSource is = new InputSource();
		  is.setCharacterStream(new StringReader(xmlSource));
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

		    transformer.transform(new DOMSource(doc), 
		         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
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
