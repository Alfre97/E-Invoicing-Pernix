package com.pernix.einvoicing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pernix.einvoicing.model.Receiver;
import com.pernix.einvoicing.repository.ReceiverRepository;

@Service
public class ReceiverService {
	@Autowired
	ReceiverRepository<Receiver> receiverRepository;

	@Transactional
	public List<Receiver> getAllReceivers() 	{
		return (List<Receiver>) receiverRepository.findAll();
	}

	@Transactional
	public void deleteReceiver(Receiver receiver) {
		receiverRepository.delete(receiver);
	}

	@Transactional
	public boolean addReceiver(Receiver receiver) {
		return receiverRepository.save(receiver) != null;
	}

	@Transactional
	public boolean updateReceiver(Receiver receiver) {
		return receiverRepository.save(receiver) != null;
	}
	
	@Transactional
	public Receiver findOneReceiver(Receiver receiver) {
		return receiverRepository.findById(receiver.getId()).get();
	}
}
