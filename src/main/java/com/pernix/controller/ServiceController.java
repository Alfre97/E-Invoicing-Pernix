package com.pernix.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import main.entities.Code;
import main.entities.Service;
import main.entities.Tax;
import main.services.CodeService;
import main.services.ServiceService;
import main.services.TaxService;

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

		try {
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
			service.setTaxList(constructTaxList(taxes, totalAmount));

			serviceService.insert(service);
		} catch (Exception e) {
			throw e;
		}
	}

	private List<Tax> constructTaxList(String taxes, String totalAmount)
			throws IllegalArgumentException, InvocationTargetException, Exception {
		ArrayList<Tax> taxesList = new ArrayList<Tax>();
		String[] taxDataList = taxes.split(", ");
		Tax tax = new Tax();
		TaxService taxService = new TaxService();

		if (taxDataList.length > 0) {
			for (int i = 0; i < taxDataList.length; i++) {
				tax.setId(Integer.parseInt(taxDataList[i]));
				tax = taxService.read(tax);
				double taxTotal = Double.parseDouble(tax.getRate()) * Integer.parseInt(totalAmount);
				tax.setTaxTotal(Double.toString(taxTotal));
				taxesList.add(tax);
				tax = new Tax();
			}
		}
		return taxesList;
	}

	private List<Code> constructCodeList(String codes) throws IllegalArgumentException, InvocationTargetException, Exception {
		ArrayList<Code> codesList = new ArrayList<Code>();
		String[] codeIdsList = codes.split(", ");
		Code code = new Code();
		CodeService codeService = new CodeService();

		if (codeIdsList.length > 0) {
			for (int i = 0; i < codeIdsList.length; i++) {
				code.setId(Integer.parseInt(codeIdsList[i]));
				code = codeService.read(code);
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
