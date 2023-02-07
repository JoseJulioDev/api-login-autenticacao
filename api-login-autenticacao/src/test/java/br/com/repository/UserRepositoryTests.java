package br.com.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.model.User;
import br.com.model.enums.Role;
import br.com.service.util.HashUtil;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void aSave() {
		String password = HashUtil.getSecureHash("123456");
		
		User user = new User(null, "Julio2", "josejulioumbelino.dev@gmail.com", 
							 password, Role.ADMINISTRATOR);
		
		User createdUser = userRepository.save(user);
		
		assertThat(createdUser.getId()).isEqualTo(2l);
	}
	
	@Test
	public void  findById() {
		Optional<User> result = userRepository.findById(2L);
		User user = result.get();
		
		String password = HashUtil.getSecureHash("123456");
		
		assertThat(user.getPassword()).isEqualTo(password);
	}
}
