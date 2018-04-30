package com.pernix.einvoicing.service.hacienda.invoicing;

import com.pernix.einvoicing.model.Invoice;

public interface InvoicerService {

    public String save(Invoice invoice);
    public String get();
}
