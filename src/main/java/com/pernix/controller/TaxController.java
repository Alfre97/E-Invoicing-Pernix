package com.pernix.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import entities.Tax;
import services.TaxService;

@RestController
public class TaxController {

	private TaxService taxService = new TaxService();

	@RequestMapping("/addTax")
	public void addService(
			@RequestParam String code, 
			@RequestParam String taxTotal,
			@RequestParam String rate, 
			@RequestParam String purchasePercentage, 
			@RequestParam String date,
			@RequestParam String taxExo, 
			@RequestParam String institutionName, 
			@RequestParam String documentNumber,
			@RequestParam String documentType) throws Exception {

		Tax tax = new Tax();
		tax.setCode(code);
		tax.setTaxTotal(taxTotal);
		tax.setRate(rate);
		tax.setPurchasePercentage(purchasePercentage);
		tax.setDate(date);
		tax.setTax(taxExo);
		tax.setInstitutionName(institutionName);
		tax.setDocumentNumber(documentNumber);
		tax.setDocumentType(documentType);

		try {
			taxService.insert(tax);
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping("/getTaxes")
	public String getServices() throws Exception {
		Tax tax = new Tax();
		Gson gson = new Gson();
		try {
			String jsonService = gson.toJson(taxService.list(tax));
			return jsonService;
		} catch (Exception e) {
			throw e;
		}
	}
}
