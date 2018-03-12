package com.pernix.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.emitter.Emitter;

import com.pernix.entity.User;

@Service
public class UserService {
	@Autowired
    UserRepository userRepository;

    public User addUser(User user) {
      try 
      {
        userRepository.save(user);
      }
      catch(Exception ex) 
      {
        throw ex;
      }
        return user;
    }
}
