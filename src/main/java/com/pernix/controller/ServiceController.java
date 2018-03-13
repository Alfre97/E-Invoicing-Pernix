package com.pernix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import entities.Service;
import services.ServiceService;

@RestController
public class ServiceController {
	@Autowired
	private ServiceService serviceService = new ServiceService();
	
	@RequestMapping("/addService")
    public ModelAndView addService( 
    		@RequestParam String amount,
    		@RequestParam String code, 
    		@RequestParam String codeType, 
    		@RequestParam String comercialUnitOfMeasurement,
    		@RequestParam String detail,
    		@RequestParam String discount, 
    		@RequestParam Integer id, 
    		@RequestParam String lineNumber, 
    		@RequestParam String priceByUnit, 
    		@RequestParam String subTotal, 
    		@RequestParam String total, 
    		@RequestParam String totalAmount,
    		@RequestParam String unitOfMeasurementName,
    		@RequestParam String unitOfMeasurementType) throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("service");
        
        Service service = new Service();
        service.setAmount(amount);
        service.setCode(code);
        service.setCodeType(codeType);
        service.setComercialUnitOfMeasurement(comercialUnitOfMeasurement);
        service.setDetail(detail);
        service.setDiscount(discount);
        service.setId(id);
        service.setLineNumber(lineNumber);
        service.setPriceByUnit(priceByUnit);
        service.setSubTotal(subTotal);
        service.setTotal(total);
        service.setTotalAmount(totalAmount);
        service.setUnitOfMeasurementName(unitOfMeasurementName);
        service.setUnitOfMeasurementType(unitOfMeasurementType);
        
        try
        {
            serviceService.insert(service);
            modelAndView.addObject("message","User added!");
        }
        catch(Exception e)
        {
            modelAndView.addObject("message", "Failed to add user!");
        }
        return modelAndView;
    }
}
