package br.com.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.model.User;
import br.com.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSavedto {
	
	@NotBlank(message="Name inválido")
	private String name;
	
	@Email(message="Email inválido")
	private String email;
	
	@Size(min=6, max=99, message="Password deve ter entre 6 a 99")
	private String password;
	
	@NotNull(message="Prefil inválido")
	private Role role;
	
	public User transformToUser() {
		User user = new User(null, this.name, this.email, this.password, this.role);
		return user;
	}
}
