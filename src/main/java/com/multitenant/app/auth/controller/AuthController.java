package com.multitenant.app.auth.controller;

import com.multitenant.app.auth.dto.LoginRequestDto;
import com.multitenant.app.auth.dto.LoginResponseDto;
import com.multitenant.app.auth.service.AuthService;
import com.multitenant.app.common.constants.api.path.AuthApiPath;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController
 *
 * Handles tenant-based authentication requests.
 *
 * This controller acts as the entry point for multi-tenant login flow
 * and ensures secure authentication using JWT tokens.
 */
@RestController
@RequestMapping(AuthApiPath.BASE)
@RequiredArgsConstructor
public class AuthController {

	// Service layer dependency for authentication operations
	private final AuthService authService;

	/**
	 * Authenticates user credentials for a specific tenant.
	 *
	 * @param request Login request containing username, password, and tenant code
	 * @return JWT token and user session details
	 */
	@PostMapping(AuthApiPath.LOGIN)
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
		return ResponseEntity.ok(authService.login(request));
	}

}