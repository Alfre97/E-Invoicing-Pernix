package com.pernix.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import entities.Code;
import entities.Service;
import entities.Tax;
import services.ServiceService;
import services.TaxService;

@RestController
public class ServiceController {

	private ServiceService serviceService = new ServiceService();

	@RequestMapping("/addService")
	public void addService(@RequestParam String amount, @RequestParam String codes,
			@RequestParam String comercialUnitOfMeasurement, @RequestParam String detail, @RequestParam String discount,
			@RequestParam String discountNature, @RequestParam String lineNumber, @RequestParam String priceByUnit,
			@RequestParam String subTotal, @RequestParam String total, @RequestParam String totalAmount,
			@RequestParam String unitOfMeasurementName, @RequestParam String unitOfMeasurementType,
			@RequestParam String taxes) throws Exception {

		Service service = new Service();
		service.setAmount(amount);
		service.setCodeList(constructCodeList(codes));
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
		service.setTaxList(constructTaxList(taxes));

		try {
			serviceService.insert(service);
			System.out.println(service.getAmount());
		} catch (Exception e) {
			throw e;
		}
	}

	private List<Tax> constructTaxList(String taxes)
			throws IllegalArgumentException, InvocationTargetException, Exception {
		ArrayList<Tax> taxesList = new ArrayList<Tax>();
		String[] taxDataList = taxes.split(", ");
		Tax tax = new Tax();
		TaxService taxService = new TaxService();

		if (taxDataList.length > 0) {            
			for (int i = 0; i < taxDataList.length; i++) {
				tax.setId(Integer.parseInt(taxDataList[i]));
				tax = taxService.read(tax);
				taxesList.add(tax);
				tax = new Tax();
			}
		}
		return taxesList;
	}

	private List<Code> constructCodeList(String codes) {
		ArrayList<Code> codesList = new ArrayList<Code>();
		String[] codeDataList = codes.split(", ");
		Code code = new Code();

		for (int i = 0; i < codeDataList.length; i = i + 2) {
			code.setCodeType(codeDataList[i]);
			code.setCode(codeDataList[i + 1]);
			codesList.add(code); 
			code = new Code();
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
