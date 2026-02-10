package com.multitenant.app.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JwtAuthenticationEntryPoint
 *
 * Handles unauthorized access attempts for secured APIs.
 *
 * Sends a standardized HTTP 401 response when authentication
 * fails due to missing or invalid JWT tokens.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * {@inheritDoc}
	 *
	 * Triggered when an unauthenticated request tries
	 * to access protected resources.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		// Return standardized unauthorized response
		response.getWriter().write("""
			{
			"status":401,
			"message":"Unauthorized access",
			"path":"%s"
			}
		""".formatted(request.getRequestURI()));
	}

}