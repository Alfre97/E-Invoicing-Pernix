package com.pernix.einvoicing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pernix.einvoicing.model.Code;
import com.pernix.einvoicing.model.Services;
import com.pernix.einvoicing.model.Tax;
import com.pernix.einvoicing.service.CodeService;
import com.pernix.einvoicing.service.ServiceServices;
import com.pernix.einvoicing.service.TaxService;

@RestController
public class ServiceController {

	@Autowired
	private ServiceServices serviceService;

	@Autowired
	private CodeService codeService;
	
	@Autowired
	private TaxService taxService;

	@RequestMapping("/addService")
	public ResponseEntity<Boolean> addService(@RequestBody Services service) throws Exception {

		try {
			service = serviceService.addServiceAndUpdate(service);
			linkCodes(service);
			linkTaxes(service);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
		}
	}
	
	private void linkCodes(Services service) {
		for (Code code : service.getCodeList()) {
			code.setService(service);
			codeService.updateCode(code);
		}
	}
	
	private void linkTaxes(Services service) {
		for (Tax tax : service.getTaxList()) {
			tax.setService(service);
			tax.setTaxTotal(service.getSubTotal() * tax.getRate());
			taxService.updateTax(tax);
		}
	}

	@RequestMapping("/getServices")
	public ResponseEntity<String> getServices() throws Exception {
		Gson gson = new Gson();
		try {
			String jsonService = gson.toJson(serviceService.getAllServices());
			return new ResponseEntity<String>(jsonService, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping("/deleteService")
	public ResponseEntity<Boolean> deleteService(@RequestParam("id") Long id) throws Exception {
		Services service = new Services();
		try {
			service.setId(id);
			serviceService.deleteService(service);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping("/modifyService")
	public ResponseEntity<Boolean> modifyService(@RequestBody Services service) throws Exception {
		Boolean result = false;
		try {
			result = serviceService.updateService(service);
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(result, HttpStatus.CONFLICT);
		}
	}
}
