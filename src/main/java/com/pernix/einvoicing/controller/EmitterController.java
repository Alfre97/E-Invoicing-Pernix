package com.pernix.einvoicing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pernix.einvoicing.model.Emitter;
import com.pernix.einvoicing.service.EmitterService;

@RestController
public class EmitterController {

	@Autowired
	private EmitterService emitterService;

	@RequestMapping("/addEmitter")
	public ResponseEntity<Boolean> addEmitter(@RequestBody Emitter emitter) throws Exception {
		try {
			emitterService.addEmitter(emitter);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping("/getEmitters")
	public ResponseEntity<String> getEmitters() throws Exception {
		Gson gson = new Gson();
		try {
			String jsonService = gson.toJson(emitterService.getAllEmitters());
			return new ResponseEntity<String>(jsonService, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping("/deleteEmitter")
	public ResponseEntity<Boolean> deleteEmitter(@RequestParam Long emitterId) throws Exception {
		Emitter emitter = new Emitter();
		try {
			emitter.setId(emitterId);
			emitterService.deleteEmitter(emitter);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping("/modifyEmitter")
	public ResponseEntity<Boolean> modifyEmitter(@RequestBody Emitter emitter) throws Exception {
		Boolean result = false;
		try {
			result = emitterService.updateEmitter(emitter);
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(result, HttpStatus.CONFLICT);
		}
	}
}
