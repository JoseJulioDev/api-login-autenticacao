package br.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	@Query("SELECT u FROM tb_user u WHERE u.email = ?1 AND u.password = ?2 ")
	public Optional<User> login(String email, String password);
	
}
