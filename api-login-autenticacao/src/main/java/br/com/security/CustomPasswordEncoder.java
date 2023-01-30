package br.com.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.service.util.HashUtil;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence charSequence) {
		String hash = HashUtil.getSecureHash(charSequence.toString());
		return hash;
	}

	@Override
	public boolean matches(CharSequence charSequence, String encodedPassword) {
		String hash = HashUtil.getSecureHash(charSequence.toString());
		return hash.equals(encodedPassword);
	}

}
