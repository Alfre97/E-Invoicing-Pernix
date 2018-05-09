package com.pernix.einvoicing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pernix.einvoicing.model.Emitter;
import com.pernix.einvoicing.repository.EmitterRepository;

@Service
public class EmitterService {
	@Autowired
	EmitterRepository<Emitter> emitterRepository;

	@Transactional
	public List<Emitter> getAllEmitters() 	{
		return (List<Emitter>) emitterRepository.findAll();
	}

	@Transactional
	public void deleteEmitter(Emitter emitter) {
		emitterRepository.delete(emitter);
	}

	@Transactional
	public boolean addEmitter(Emitter emitter) {
		return emitterRepository.save(emitter) != null;
	}

	@Transactional
	public boolean updateEmitter(Emitter emitter) {
		return emitterRepository.save(emitter) != null;
	}
	
	@Transactional
	public Emitter findOneEmitter(Emitter emitter) {
		return emitterRepository.findById(emitter.getId()).get();
	}
}
