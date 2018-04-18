package com.pernix.entities;

public class Invoice {

    private String key;
    //Fecha de la factura en formato [yyyy-MM-dd'T'HH:mm:ssZ] como se define en [http://tools.ietf.org/html/rfc3339#section-5.6
    private String date;
    private UserEmitterReceiver emitter;
    private UserEmitterReceiver recipient;
    private String xmlInvoice;
    
    public Invoice() {
    	
    }
    
	public Invoice(String key, String date, UserEmitterReceiver emitter, UserEmitterReceiver recipient, String xmlInvoice) {
		super();
		this.key = key;
		this.date = date;
		this.emitter = emitter;
		this.recipient = recipient;
		this.xmlInvoice = xmlInvoice;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public UserEmitterReceiver getEmitter() {
		return emitter;
	}
	public void setEmitter(UserEmitterReceiver emitter) {
		this.emitter = emitter;
	}
	public UserEmitterReceiver getRecipient() {
		return recipient;
	}
	public void setRecipient(UserEmitterReceiver recipient) {
		this.recipient = recipient;
	}
	public String getXmlInvoice() {
		return xmlInvoice;
	}
	public void setXmlInvoice(String xmlInvoice) {
		this.xmlInvoice = xmlInvoice;
	}
}
