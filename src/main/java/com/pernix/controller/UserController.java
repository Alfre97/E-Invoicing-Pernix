package com.pernix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import entities.UserEmitterReceiver;
import services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService = new UserService();
	
	@RequestMapping("/addUser")
    public ModelAndView addUser(@RequestParam(value="userName") String name, 
    		@RequestParam String comercialName,
    		@RequestParam String identificationType, 
    		@RequestParam String identificationNumber, 
    		@RequestParam String locationProvinceType,
    		@RequestParam String locationProvinceName,
    		@RequestParam String locationCantonType, 
    		@RequestParam String locationCantonName, 
    		@RequestParam String locationDistrictType, 
    		@RequestParam String locationDistrictName, 
    		@RequestParam String locationNeighborhoodType, 
    		@RequestParam String locationNeighborhoodName, 
    		@RequestParam String locationSignals,
    		@RequestParam String phoneCountryCode,
    		@RequestParam String phoneNumber,
    		@RequestParam String faxCountryCode,
    		@RequestParam String faxNumber,
    		@RequestParam String email) throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("user");
        
        UserEmitterReceiver user = new UserEmitterReceiver();
        user.setName(name);
        user.setComercialName(comercialName);
        user.setIdentificationType(identificationType);
        user.setIdentificationNumber(identificationNumber);
        user.setLocationProvinceType(locationProvinceType);
        user.setLocationProvinceName(locationProvinceName);
        user.setLocationCantonType(locationCantonType);
        user.setLocationCantonName(locationCantonName);
        user.setLocationDistrictType(locationDistrictType);
        user.setLocationDistrictName(locationDistrictName);
        user.setLocationNeighborhoodType(locationNeighborhoodType);
        user.setLocationNeighborhoodName(locationNeighborhoodName);
        user.setOtherSignals(locationSignals);
        user.setPhoneCountryCode(phoneCountryCode);
        user.setPhoneNumber(phoneNumber);
        user.setFaxCountryCode(faxCountryCode);
        user.setFaxNumber(faxNumber);
        user.setEmail(email);
        
        try
        {
            userService.insert(user);
            modelAndView.addObject("message","User added!");
        }
        catch(Exception e)
        {
            modelAndView.addObject("message", "Failed to add user!");
        }
        return modelAndView;
    }
}
