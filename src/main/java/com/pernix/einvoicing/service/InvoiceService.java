package com.pernix.einvoicing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pernix.einvoicing.model.Invoice;
import com.pernix.einvoicing.repository.InvoiceRepository;

@Service
public class InvoiceService {
	@Autowired
	InvoiceRepository<Invoice> invoiceRepository;

	@Transactional
	public List<Invoice> getAllInvoices() 	{
		return (List<Invoice>) invoiceRepository.findAll();
	}

	@Transactional
	public void deleteInvoice(Invoice invoice) {
		invoiceRepository.delete(invoice);
	}

	@Transactional
	public boolean addInvoice(Invoice invoice) {
		return invoiceRepository.save(invoice) != null;
	}

	@Transactional
	public boolean updateInvoice(Invoice invoice) {
		return invoiceRepository.save(invoice) != null;
	}
	
	@Transactional
	public Invoice findOneInvoice(Invoice invoice) {
		return invoiceRepository.findById(invoice.getId()).get();
	}
}
