package com.pernix.einvoicing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pernix.einvoicing.model.Tax;
import com.pernix.einvoicing.service.TaxService;

@RestController
public class TaxController {
 
	@Autowired
	private TaxService taxService;

	@RequestMapping("/addTax")
	public ResponseEntity<Boolean> addTax(@RequestBody Tax tax) throws Exception {
		try {
			taxService.addTax(tax);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping("/getTaxes")
	public ResponseEntity<String> getTaxes() throws Exception {
		Gson gson = new Gson();
		try {
			String jsonService = gson.toJson(taxService.getAllTaxes());
			return new ResponseEntity<String>(jsonService, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping("/getNotLinkedTaxes")
	public ResponseEntity<String> getNotLinkedTaxes() throws Exception {
		Gson gson = new Gson();
		try {
			String jsonService = gson.toJson(taxService.getNotLinkedTaxes());
			return new ResponseEntity<String>(jsonService, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping("/deleteTax")
	public ResponseEntity<Boolean> deleteTax(@RequestParam Long taxId) throws Exception {
		Tax tax = new Tax();
		try {
			tax.setId(taxId);
			taxService.deleteTax(tax);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping("/modifyTax")
	public ResponseEntity<Boolean> modifyTax(@RequestBody Tax tax) throws Exception {
		Boolean result = false;
		try {
			result = taxService.updateTax(tax);
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(result, HttpStatus.CONFLICT);
		}
	}
}
