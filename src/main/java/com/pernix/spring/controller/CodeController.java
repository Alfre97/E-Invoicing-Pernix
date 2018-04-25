package com.pernix.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pernix.spring.model.Code;
import com.pernix.spring.services.CodeService;

@RestController
public class CodeController {
	
	CodeService codeService = new CodeService();

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
