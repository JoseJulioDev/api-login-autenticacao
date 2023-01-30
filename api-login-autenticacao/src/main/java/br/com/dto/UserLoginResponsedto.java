package br.com.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginResponsedto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String token;
	private Long expireIn;
	private String tokenProvider;
}
