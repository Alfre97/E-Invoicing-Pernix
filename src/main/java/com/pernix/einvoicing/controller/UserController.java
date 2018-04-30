package com.pernix.einvoicing.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pernix.einvoicing.model.UserEmitterReceiver;
import com.pernix.einvoicing.service.dbservices.UserEmitterReceiverService;

@RestController
public class UserController {
	
	
	private UserEmitterReceiverService userService = new UserEmitterReceiverService();
	
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
            userService.addUser(user);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
	
	@RequestMapping("/getEmitters")
	public String getEmitters() throws Exception {
		Gson gson= new Gson();
		try 
		{
			String json= gson.toJson(userService.getAllUsers());
			return json;
		}
		catch(Exception e) 
		{
			throw e;
		}
	}
	
	@RequestMapping("/getReceivers")
	public String getReceivers() throws Exception {
		Gson gson= new Gson();
		try 
		{
			String json= gson.toJson(userService.getAllUsers());
			return json;
		}
		catch(Exception e) 
		{
			throw e;
		}
	}
}
