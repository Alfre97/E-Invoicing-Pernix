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
	
	
	private UserService userService = new UserService();
	
	@RequestMapping("/addUser")
    public void addUser(@RequestParam(value="userName") String name, 
    		@RequestParam String comercialName,
    		@RequestParam String identificationType, 
    		@RequestParam String identificationNumber, 
    		@RequestParam String locationProvinceName,
    		@RequestParam String locationCantonName, 
    		@RequestParam String locationDistrictName, 
    		@RequestParam String locationNeighborhoodName, 
    		@RequestParam String locationSignals,
    		@RequestParam String phoneCountryCode,
    		@RequestParam String phoneNumber,
    		@RequestParam String faxCountryCode,
    		@RequestParam String faxNumber,
    		@RequestParam String email,
    		@RequestParam String userType) throws Exception
    {
        
        UserEmitterReceiver user = new UserEmitterReceiver();
        user.setName(name);
        user.setComercialName(comercialName);
        user.setIdentificationType(identificationType);
        user.setIdentificationNumber(identificationNumber);
        user.setLocationProvinceName(locationProvinceName);
        user.setLocationCantonName(locationCantonName);
        user.setLocationDistrictName(locationDistrictName);
        user.setLocationNeighborhoodName(locationNeighborhoodName);
        user.setOtherSignals(locationSignals);
        user.setPhoneCountryCode(phoneCountryCode);
        user.setPhoneNumber(phoneNumber);
        user.setFaxCountryCode(faxCountryCode);
        user.setFaxNumber(faxNumber);
        user.setEmail(email);
        user.setUserType(userType);
        
        
        try
        {
            userService.insert(user);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
