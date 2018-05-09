package com.pernix.einvoicing.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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

	@RequestMapping("/addService")
	public ResponseEntity<Boolean> addService(@RequestParam String amount, 
			@RequestParam String codes,
			@RequestParam String comercialUnitOfMeasurement, 
			@RequestParam String detail, 
			@RequestParam String discount,
			@RequestParam String discountNature, 
			@RequestParam String lineNumber, 
			@RequestParam String priceByUnit,
			@RequestParam String subTotal, 
			@RequestParam String total, 
			@RequestParam String totalAmount,
			@RequestParam String unitOfMeasurementName, 
			@RequestParam String unitOfMeasurementType,
			@RequestParam String taxes) throws Exception {

		try {
			Services service = new Services();
			service.setAmount(amount);
			service.setComercialUnitOfMeasurement(comercialUnitOfMeasurement);
			service.setDetail(detail);
			service.setDiscount(discount);
			service.setDiscountNature(discountNature);
			service.setLineNumber(lineNumber);
			service.setPriceByUnit(priceByUnit);
			service.setSubTotal(subTotal);
			service.setTotal(total);
			service.setTotalAmount(totalAmount);
			service.setUnitOfMeasurementName(unitOfMeasurementName);
			service.setUnitOfMeasurementType(unitOfMeasurementType);
			
			service.setCodeList(constructCodeList(codes, service));
			service.setTaxList(constructTaxList(taxes, service));
			
			serviceService.addService(service);	
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(HttpStatus.CONFLICT);
		}
	}

	private List<Tax> constructTaxList(String taxes, Services service)
			throws IllegalArgumentException, InvocationTargetException, Exception {
		ArrayList<Tax> taxesList = new ArrayList<Tax>();
		String[] taxDataList = taxes.split(",");
		Tax tax = new Tax();
		TaxService taxService = new TaxService();
		double taxAmount = 0.0;

		if (taxDataList.length > 0) {
			for (int i = 0; i < taxDataList.length; i++) {
				tax.setId(Long.parseLong(taxDataList[i].substring(0, taxDataList[i].length() - 1)));	
				tax = taxService.findOneTax(tax);
				taxAmount = Double.parseDouble(tax.getRate()) * Double.parseDouble(service.getSubTotal());
				tax.setTaxTotal(Double.toString(taxAmount));
				taxesList.add(tax);
				tax = new Tax();
			}
		}
		return taxesList;
	}

	private List<Code> constructCodeList(String codes, Services service) throws IllegalArgumentException, InvocationTargetException, Exception {
		ArrayList<Code> codesList = new ArrayList<Code>();
		String[] codeIdsList = codes.split("\\s*,\\s*");
		Code code = new Code();
		CodeService codeService = new CodeService();

		if (codeIdsList.length > 0) {
			for (int i = 0; i < codeIdsList.length; i++) {
				code.setId(Long.parseLong(codeIdsList[i]));
				code = codeService.findOneCode(code);
				codesList.add(code);
				code = new Code();
			}
		}

		return codesList;
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
	public ResponseEntity<Boolean> deleteService(@RequestParam Long serviceId) throws Exception {
		Services service = new Services();
		try {
			service.setId(serviceId);
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