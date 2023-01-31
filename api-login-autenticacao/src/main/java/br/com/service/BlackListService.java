package br.com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.model.BlackList;
import br.com.repository.BlackListRepository;

@Service
public class BlackListService {

	@Autowired
	private BlackListRepository blackListRepository;

	public BlackList save(String token) {
		BlackList blackList = new BlackList();
		blackList.setToken(token);

		BlackList createdToken = blackListRepository.save(blackList);
		return createdToken;
	}
	
	public BlackList getByJwt(String token) {
		Optional<BlackList> result = blackListRepository.findByToken(token);

		return result.orElse(null);
	}

	public void delete(BlackList blackList) {
		blackListRepository.delete(blackList);
	}

}
