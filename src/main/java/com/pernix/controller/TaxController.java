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
			@RequestParam(value="code") String code, 
			@RequestParam(value="rate") String rate, 
			@RequestParam(value="purchasePercentage") String purchasePercentage, 
			@RequestParam(value="date") String date,
			@RequestParam(value="institutionName") String institutionName, 
			@RequestParam(value="documentNumber") String documentNumber,
			@RequestParam(value="documentType") String documentType) throws Exception {
		
		System.out.println(code + rate + purchasePercentage + date + institutionName + documentNumber + documentType);

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
