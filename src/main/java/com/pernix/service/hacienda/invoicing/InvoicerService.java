package com.pernix.service.hacienda.invoicing;

import com.pernix.entities.Invoice;

public interface InvoicerService {

    public String save(Invoice invoice);
    public String get();
}
