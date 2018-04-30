package com.pernix.einvoicing.service.dbservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pernix.einvoicing.model.Services;
import com.pernix.einvoicing.repository.ServiceRepository;

@Service
public class ServiceServices {
	@Autowired
	ServiceRepository<Services> serviceRepository;

	@Transactional
	public List<Services> getAllServices() 	{
		return (List<Services>) serviceRepository.findAll();
	}

	@Transactional
	public void deleteService(Services service) {
		serviceRepository.delete(service);
	}

	@Transactional
	public boolean addService(Services service) {
		return serviceRepository.save(service) != null;
	}

	@Transactional
	public boolean updateService(Services service) {
		return serviceRepository.save(service) != null;
	}
	
	@Transactional
	public Services findOneService(Services service) {
		return serviceRepository.findById(service.getId()).get();
	}
}
