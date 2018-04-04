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
	public void addTax(
			@RequestParam(name="code") String code, 
			@RequestParam(name="rate") String rate, 
			@RequestParam(name="purchasePercentage") String purchasePercentage, 
			@RequestParam(name="date") String date,
			@RequestParam(name="institutionName") String institutionName, 
			@RequestParam(name="documentNumber") String documentNumber,
			@RequestParam(name="documentType") String documentType) throws Exception {
		
		Tax tax = new Tax();
		tax.setCode(code);
		tax.setRate(rate);
		tax.setPurchasePercentage(purchasePercentage);
		tax.setDate(date);
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
