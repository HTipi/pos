package com.spring.miniposbackend.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: "+ authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ព័ត៌មានអ្នកមិនត្រឹមត្រូវទេ");
	}
	
}
