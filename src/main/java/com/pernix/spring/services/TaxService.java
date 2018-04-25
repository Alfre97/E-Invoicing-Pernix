package com.pernix.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pernix.spring.model.Tax;
import com.pernix.spring.repository.TaxRepository;

@Service
public class TaxService {
	@Autowired
	TaxRepository<Tax> taxRepository;

	@Transactional
	public List<Tax> getAllTaxes() 	{
		return (List<Tax>) taxRepository.findAll();
	}

	@Transactional
	public void deleteTax(Tax tax) {
		taxRepository.delete(tax);
	}

	@Transactional
	public boolean addTax(Tax tax) {
		return taxRepository.save(tax) != null;
	}

	@Transactional
	public boolean updateTax(Tax tax) {
		return taxRepository.save(tax) != null;
	}
	
	@Transactional
	public Tax findOneTax(Tax tax) {
		return taxRepository.findById(tax.getId()).get();
	}
}
