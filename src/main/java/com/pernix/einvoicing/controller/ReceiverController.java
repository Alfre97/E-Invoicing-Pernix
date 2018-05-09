package com.pernix.einvoicing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pernix.einvoicing.model.Receiver;
import com.pernix.einvoicing.service.ReceiverService;

@RestController
public class ReceiverController {
	
	@Autowired
	private ReceiverService receiverService;
	
	@RequestMapping("/addReceiver")
    public ResponseEntity<Boolean> addUser(@RequestParam(value="userName") String name, 
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
        
        Receiver receiver = new Receiver();
        receiver.setName(name);
        receiver.setComercialName(comercialName);
        receiver.setIdentificationType(identificationType);
        receiver.setIdentificationNumber(identificationNumber);
        receiver.setLocationProvinceName(locationProvinceName);
        receiver.setLocationCantonName(locationCantonName);
        receiver.setLocationDistrictName(locationDistrictName);
        receiver.setLocationNeighborhoodName(locationNeighborhoodName);
        receiver.setOtherSignals(locationSignals);
        receiver.setPhoneCountryCode(phoneCountryCode);
        receiver.setPhoneNumber(phoneNumber);
        receiver.setFaxCountryCode(faxCountryCode);
        receiver.setFaxNumber(faxNumber);
        receiver.setEmail(email);
        
        
        try
        {
            receiverService.addReceiver(receiver);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(HttpStatus.CONFLICT);
        }
    }
	
	@RequestMapping("/getReceivers")
	public ResponseEntity<String> getEmitters() throws Exception {
		Gson gson = new Gson();
		try 
		{
			String jsonService = gson.toJson(receiverService.getAllReceivers());
			return new ResponseEntity<String>(jsonService, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping("/deleteReceiver")
	public ResponseEntity<Boolean> deleteEmitter(@RequestParam Long userId) throws Exception {
		Receiver receiver = new Receiver();
		try {
			receiver.setId(userId);
			receiverService.deleteReceiver(receiver);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping("/modifyReceiver")
	public ResponseEntity<Boolean> modifyUser(@RequestBody Receiver receiver) throws Exception {
		Boolean result = false;
		try {
			result = receiverService.updateReceiver(receiver);
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(result, HttpStatus.CONFLICT);
		}
	}
}
