package br.com.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.service.util.HashUtil;

@Component
public class CustomPasswordEnconder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		String hash = HashUtil.getSecureHash(rawPassword.toString());
		return hash;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String hash = HashUtil.getSecureHash(rawPassword.toString());
		return hash.equals(encodedPassword);
	}

}
