package com.pernix.service.hacienda.invoicing;

import entities.Invoice;

public interface InvoicerService {

    public String save(Invoice invoice);
    public String get();
}
