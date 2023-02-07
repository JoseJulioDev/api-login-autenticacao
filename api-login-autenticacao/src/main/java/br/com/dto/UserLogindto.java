package br.com.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserLogindto {
	
	@Email(message = "Email inválido")
	private String email;
	
	@NotBlank(message = "Password inválido")
	private String password;
}
