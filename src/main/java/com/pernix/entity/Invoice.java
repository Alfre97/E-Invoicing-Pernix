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

    public class Identification{
        private String type;
        private String id;
        
        public Identification(){}

        public Identification(String type, String id){
            this.type = type;
            this.id = id;
        }

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
    }
}
