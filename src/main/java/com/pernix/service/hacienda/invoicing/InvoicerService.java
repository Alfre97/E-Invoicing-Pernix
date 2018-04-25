package com.pernix.service.hacienda.invoicing;

import com.pernix.spring.model.Invoice;

public interface InvoicerService {

    public String save(Invoice invoice);
    public String get();
}
