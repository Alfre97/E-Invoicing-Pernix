package com.pernix.einvoicing.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void addCode(
			@RequestParam String codeType, 
			@RequestParam String code) throws Exception {
		
		Code cod = new Code();
		cod.setCodeType(codeType);
		cod.setCode(code);

		try {
			codeService.addCode(cod);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping("/getCodes")
	public String getCodes() throws Exception {
		Gson gson = new Gson();
		try {
			String jsonService = gson.toJson(codeService.getAllCodes());
			return jsonService;
		} catch (Exception e) {
			throw e;
		}
	}
}
