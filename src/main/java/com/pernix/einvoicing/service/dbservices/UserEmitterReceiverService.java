package com.pernix.einvoicing.service.dbservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pernix.einvoicing.model.UserEmitterReceiver;
import com.pernix.einvoicing.repository.UserEmitterReceiverRepository;

@Service
public class UserEmitterReceiverService {
	@Autowired
	UserEmitterReceiverRepository<UserEmitterReceiver> userRepository;

	@Transactional
	public List<UserEmitterReceiver> getAllUsers() 	{
		return (List<UserEmitterReceiver>) userRepository.findAll();
	}

	@Transactional
	public void deleteUser(UserEmitterReceiver user) {
		userRepository.delete(user);
	}

	@Transactional
	public boolean addUser(UserEmitterReceiver user) {
		return userRepository.save(user) != null;
	}

	@Transactional
	public boolean updateUser(UserEmitterReceiver user) {
		return userRepository.save(user) != null;
	}
	
	@Transactional
	public UserEmitterReceiver findOneUser(UserEmitterReceiver user) {
		return userRepository.findById(user.getId()).get();
	}
}
