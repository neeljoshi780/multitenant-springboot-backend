package com.multitenant.app.auth.service;

import com.multitenant.app.auth.dto.LoginRequestDto;
import com.multitenant.app.auth.dto.LoginResponseDto;
import com.multitenant.app.common.exception.TenantNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * AuthService
 *
 * Service contract responsible for handling authentication operations
 * in a multi-tenant environment.
 *
 * This interface defines tenant-aware authentication behavior and
 * abstracts implementation details such as:
 * - Tenant validation
 * - Dynamic database routing
 * - Credential verification
 * - JWT token generation
 *
 * This abstraction helps maintain loose coupling between controller
 * and authentication implementation logic.
 */
public interface AuthService {

	/**
	 * Authenticates a user for a specific tenant and generates
	 * a JWT access token upon successful authentication.
	 *
	 * Processing Flow:
	 * 1. Validate tenant identifier
	 * 2. Resolve tenant database configuration
	 * 3. Authenticate user credentials
	 * 4. Generate JWT token with tenant and role claims
	 * 5. Return authentication response
	 *
	 * @param request login request containing tenant code, username, and password
	 *
	 * @return LoginResponseDto containing JWT token and authenticated user details
	 *
	 * @throws BadCredentialsException if authentication fails
	 * @throws TenantNotFoundException if tenant is invalid or inactive
	 */
	LoginResponseDto login(LoginRequestDto request);

}