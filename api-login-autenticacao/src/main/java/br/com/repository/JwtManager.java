package br.com.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.constant.SecurityConstants;
import br.com.dto.UserLoginResponsedto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtManager {

	public UserLoginResponsedto createdToken(String email, List<String> roles) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, SecurityConstants.JWT_EXP_MINUTOS);
		
		String jwt = Jwts.builder()
				.setSubject(email)
				.setExpiration(calendar.getTime())
				.claim(SecurityConstants.JWT_ROLE_KEY, roles)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.API_KEY.getBytes())
			    .compact();
		
		Long expireIn = calendar.getTimeInMillis(); 
		
		return new UserLoginResponsedto(jwt, expireIn, SecurityConstants.JWT_PROVIDER);
	}
}
