package br.com.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.exception.NotFoundException;
import br.com.model.User;
import br.com.repository.UserRepository;
import br.com.service.util.HashUtil;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		String hash = HashUtil.getSecureHash(user.getPassword());

		user.setPassword(hash);

		User createdUser = userRepository.save(user);
		return createdUser;
	}

	public User login(String email, String password) {
		String hash = HashUtil.getSecureHash(password);

		Optional<User> result = userRepository.login(email, hash);
		return result.orElseThrow(
				() -> new NotFoundException("Não foi encontrado um usuário com email e password informado"));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> result = userRepository.findByEmail(username);
		
		if(!result.isPresent()) throw new UsernameNotFoundException("Não existe usuário com e-mail " + username);
		
		User user = result.get();
		
		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
		
		org.springframework.security.core.userdetails.User userSpring = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
		
		return userSpring;
	}

}
