package com.pernix.service.hacienda.invoicing;

import main.entities.Invoice;

public interface InvoicerService {

    public String save(Invoice invoice);
    public String get();
}
