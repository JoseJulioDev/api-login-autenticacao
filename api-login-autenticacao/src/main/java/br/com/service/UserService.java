package br.com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.model.User;
import br.com.repository.UserRepository;
import br.com.service.util.HashUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User save(User user) {
		String hash =  HashUtil.getSecureHash(user.getPassword());
		
		user.setPassword(hash);
		
		User createdUser = userRepository.save(user);
		return createdUser;
	}
	
	public User login(String email, String password) {
		String hash = HashUtil.getSecureHash(password);
		
		Optional<User> loggedUser = userRepository.login(email, hash);
		return loggedUser.get();
	}
	
}
