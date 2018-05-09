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
    public ResponseEntity<Boolean> addReceiver(@RequestBody Receiver receiver) throws Exception
    {
        try
        {
            receiverService.addReceiver(receiver);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(HttpStatus.CONFLICT);
        }
    }
	
	@RequestMapping("/getReceivers")
	public ResponseEntity<String> getReceivers() throws Exception {
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
	public ResponseEntity<Boolean> deleteReceiver(@RequestParam Long userId) throws Exception {
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
	public ResponseEntity<Boolean> modifyReceiver(@RequestBody Receiver receiver) throws Exception {
		Boolean result = false;
		try {
			result = receiverService.updateReceiver(receiver);
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(result, HttpStatus.CONFLICT);
		}
	}
}
