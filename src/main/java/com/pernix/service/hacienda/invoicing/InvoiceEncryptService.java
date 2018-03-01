package com.pernix.service.hacienda.invoicing;

public interface InvoiceEncryptService {
	public String sign(String xml) throws Exception;
	public String verifySign(String xml) throws Exception;
}
