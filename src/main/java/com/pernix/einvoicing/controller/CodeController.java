package com.pernix.einvoicing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pernix.einvoicing.model.Code;
import com.pernix.einvoicing.service.CodeService;

@RestController
public class CodeController {
	
	@Autowired
	CodeService codeService;

	@RequestMapping("/addCode")
	public ResponseEntity<Boolean> addCode(
			@RequestParam String codeType, 
			@RequestParam String code) throws Exception {
		
		Code cod = new Code();
		cod.setCodeType(codeType);
		cod.setCode(code);

		try {
			codeService.addCode(cod);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping("/getCodes")
	public ResponseEntity<String> getCodes() throws Exception {
		Gson gson = new Gson();
		try {
			String jsonService = gson.toJson(codeService.getAllCodes());
			return new ResponseEntity<String>(jsonService, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping("/getNotLinkedCodes")
	public ResponseEntity<String> getNotLinkedCodes() throws Exception {
		Gson gson = new Gson();
		try {
			String jsonService = gson.toJson(codeService.getNotLinkedCodes());
			return new ResponseEntity<String>(jsonService, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping("/deleteCode")
	public ResponseEntity<Boolean> deleteCode(@RequestParam Long codeId) throws Exception {
		Code code = new Code();
		try {
			code.setId(codeId);
			codeService.deleteCode(code);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
		}
	}
}
