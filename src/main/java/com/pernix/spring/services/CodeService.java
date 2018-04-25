package com.pernix.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pernix.spring.model.Code;
import com.pernix.spring.repository.CodeRepository;

@Service
public class CodeService {
	@Autowired
	CodeRepository<Code> codeRepository;

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
}
