package br.com.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.model.BlackList;

public interface BlackListRepository extends CrudRepository<BlackList, Long>{
	
	public Optional<BlackList> findByToken(String token);

}
