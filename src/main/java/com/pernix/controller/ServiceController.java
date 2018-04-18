package com.pernix.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import com.pernix.entities.Code;
import com.pernix.entities.Service;
import com.pernix.entities.Tax;
import com.pernix.services.CodeService;
import com.pernix.services.ServiceService;
import com.pernix.services.TaxService;

@RestController
public class ServiceController {

	private ServiceService serviceService = new ServiceService();

	@RequestMapping("/addService")
	public void addService(@RequestParam String amount, 
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
			Service service = new Service();
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
			
			service = serviceService.insert(service);
			System.out.println(service.getId());
			
			service.setCodeList(constructCodeList(codes, service));
			service.setTaxList(constructTaxList(taxes, service));

			service = serviceService.insert(service);
			
		} catch (Exception e) {
			throw e;
		}
	}

	private List<Tax> constructTaxList(String taxes, Service service)
			throws IllegalArgumentException, InvocationTargetException, Exception {
		ArrayList<Tax> taxesList = new ArrayList<Tax>();
		String[] taxDataList = taxes.split(", ");
		Tax tax = new Tax();
		TaxService taxService = new TaxService();
		double taxAmount = 0.0;

		if (taxDataList.length > 0) {
			for (int i = 0; i < taxDataList.length; i++) {
				tax.setId(Integer.parseInt(taxDataList[i]));	
				tax = taxService.read(tax);
				taxAmount = Double.parseDouble(tax.getRate()) * Double.parseDouble(service.getSubTotal());
				tax.setTaxTotal(Double.toString(taxAmount));
				tax.setServiceTax(service);
				tax = taxService.modify(tax);
				taxesList.add(tax);
				tax = new Tax();
			}
		}
		return taxesList;
	}

	private List<Code> constructCodeList(String codes, Service service) throws IllegalArgumentException, InvocationTargetException, Exception {
		ArrayList<Code> codesList = new ArrayList<Code>();
		String[] codeIdsList = codes.split(", ");
		Code code = new Code();
		CodeService codeService = new CodeService();

		if (codeIdsList.length > 0) {
			for (int i = 0; i < codeIdsList.length; i++) {
				code.setId(Integer.parseInt(codeIdsList[i]));
				code = codeService.read(code);
				code.setServiceCode(service);
				code = codeService.insert(code);
				codesList.add(code);
				code = new Code();
			}
		}

		return codesList;
	}

	@RequestMapping("/getServices")
	public String getServices() throws Exception {
		Service service = new Service();
		Gson gson = new Gson();
		try {
			String jsonService = gson.toJson(serviceService.list(service));
			return jsonService;
		} catch (Exception e) {
			throw e;
		}
	}
}
