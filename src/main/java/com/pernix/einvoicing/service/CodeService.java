package com.pernix.einvoicing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pernix.einvoicing.model.Code;
import com.pernix.einvoicing.repository.CodeJPARepository;
import com.pernix.einvoicing.repository.CodeRepository;

@Service
public class CodeService {
	@Autowired
	CodeRepository<Code> codeRepository;
	
	@Autowired
	CodeJPARepository<Code> codeJPARepository;

	@Transactional
	public List<Code> getAllCodes() 	{
		return (List<Code>) codeRepository.findAll();
	}

	@Transactional
	public void deleteCode(Code code) {
		codeRepository.delete(code);
	}

	@Transactional
	public boolean addCode(Code code) {
		return codeRepository.save(code) != null;
	}

	@Transactional
	public boolean updateCode(Code code) {
		return codeRepository.save(code) != null;
	}
	
	@Transactional
	public Code findOneCode(Code code) {
		return codeRepository.findById(code.getId()).get();
	}
	
	@Transactional
	public List<Code> getNotLinkedCodes() {
		return (List<Code>) codeJPARepository.findNoService();
	}
}
