package com.multitenant.app.security;

import com.multitenant.app.common.constants.api.path.AuthApiPath;
import com.multitenant.app.common.constants.api.path.TenantAdminApiPath;
import com.multitenant.app.security.jwt.JwtAuthenticationEntryPoint;
import com.multitenant.app.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig
 *
 * Central security configuration for the application.
 *
 * Configures:
 * - JWT-based stateless authentication
 * - Password encryption
 * - Public and protected API access rules
 * - JWT filter integration in security chain
 *
 * Ensures secure access control for all tenant APIs.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	/* Handles unauthorized (401) access responses */
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;

	/* JWT validation filter */
	private final JwtAuthenticationFilter jwtFilter;

	/**
	 * Provides BCrypt password encoder for secure password hashing.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Exposes AuthenticationManager required
	 * by AuthController for user login authentication.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * Configures HTTP security behavior.
	 *
	 * Configuration includes:
	 * - Disable CSRF for REST APIs
	 * - Stateless session management
	 * - Public access for auth endpoints
	 * - Secure all other APIs
	 * - Register JWT authentication filter
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(org.springframework.security.config.Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(AuthApiPath.BASE + AuthApiPath.LOGIN).permitAll()
				.requestMatchers(TenantAdminApiPath.BASE + TenantAdminApiPath.REGISTER).permitAll()
				.anyRequest().authenticated()
			)
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}