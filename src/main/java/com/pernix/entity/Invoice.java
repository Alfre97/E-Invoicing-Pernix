package com.pernix.entity;

public class Invoice {

    private String key;
    //Fecha de la factura en formato [yyyy-MM-dd'T'HH:mm:ssZ] como se define en [http://tools.ietf.org/html/rfc3339#section-5.6
    private String date;
    private Identification emitter;
    private Identification recipient;
    private String xmlInvoice;

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

    public Identification getEmitter() {
        return emitter;
    }

    public void setEmitter(Identification emitter) {
        this.emitter = emitter;
    }

    public Identification getRecipient() {
        return recipient;
    }

    public void setRecipient(Identification recipient) {
        this.recipient = recipient;
    }

    public String getXmlInvoice() {
        return xmlInvoice;
    }

    public void setXmlInvoice(String xmlInvoice) {
        this.xmlInvoice = xmlInvoice;
    }

    
}
