package br.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT u FROM tb_user u WHERE u.email = ?1 AND u.password = ?2 ")
	public Optional<User> login(String email, String password);
	
	public Optional<User> findByEmail(String email);
}
