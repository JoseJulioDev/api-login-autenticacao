package br.com.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.constant.SecurityConstants;
import br.com.dto.UserLoginResponsedto;
import br.com.dto.UserLogindto;
import br.com.dto.UserSavedto;
import br.com.model.BlackList;
import br.com.model.User;
import br.com.repository.JwtManager;
import br.com.service.BlackListService;
import br.com.service.UserService;

@RestController
@RequestMapping(value = "users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BlackListService blackListService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtManager jwtManager;

	@PostMapping
	public ResponseEntity<User> save(@RequestBody @Valid UserSavedto userdto) {
		User userToSave = userdto.transformToUser();
		
		User createdUser = userService.save(userToSave);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") Long id) {
		User user = userService.getById(id);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/login")
	@CrossOrigin("*")
	public ResponseEntity<UserLoginResponsedto> login(@RequestBody @Valid UserLogindto user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		Authentication auth = authManager.authenticate(token);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		org.springframework.security.core.userdetails.User userSpring =
				(org.springframework.security.core.userdetails.User) auth.getPrincipal();
		
		String email = userSpring.getUsername();
		List<String> roles = userSpring.getAuthorities()
					.stream()
					.map(authority -> authority.getAuthority())
					.collect(Collectors.toList());
		
		UserLoginResponsedto userLoginResponsedto = jwtManager.createdToken(email, roles);
		
		BlackList tokenBlackList = blackListService.getByJwt(userLoginResponsedto.getToken());
		if (tokenBlackList != null) {
			blackListService.delete(tokenBlackList);
		}
		
		return ResponseEntity.ok(userLoginResponsedto);
	}
	
	@DeleteMapping("/logout")
	@CrossOrigin("*")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		token = token.replace(SecurityConstants.JWT_PROVIDER, "");
		
		blackListService.save(token);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
