package com.multitenant.app.auth.service.impl;

import com.multitenant.app.auth.dto.LoginRequestDto;
import com.multitenant.app.auth.dto.LoginResponseDto;
import com.multitenant.app.auth.service.AuthService;
import com.multitenant.app.context.TenantContext;
import com.multitenant.app.master.service.TenantLookupService;
import com.multitenant.app.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * AuthServiceImpl
 *
 * Concrete implementation of AuthService that handles
 * tenant-aware user authentication and JWT token generation.
 *
 * Integrates tenant resolution, Spring Security authentication,
 * and token creation in a single login workflow.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	/* Resolves tenant database details from MASTER database */
	private final TenantLookupService tenantLookupService;

	/* Spring Security authentication manager */
	private final AuthenticationManager authenticationManager;

	/* Generates JWT tokens */
	private final JwtTokenProvider jwtProvider;

	/**
	 * {@inheritDoc}
	 *
	 * Implementation Notes:
	 * - Resolves tenant configuration from master database
	 * - Sets tenant context for dynamic datasource routing
	 * - Authenticates user using Spring Security
	 * - Generates tenant-aware JWT token
	 *
	 * Thread Safety:
	 * Tenant context is cleared after authentication
	 * to avoid cross-tenant data leakage.
	 */
	@Override
	public LoginResponseDto login(LoginRequestDto request) {
		try {
			// Resolve tenant database configuration
			var dbConfig = tenantLookupService.findTenantDatabase(request.getCompanyCode());

			if (dbConfig == null) {
				throw new RuntimeException("Invalid tenant code");
			}

			// Set tenant context for routing datasource
			TenantContext.setTenantDb(dbConfig.getDbName());

			// Authenticate user credentials
			var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					request.getUsername(),
					request.getPassword()
				)
			);

			// Extract user role safely
			var role = authentication.getAuthorities().iterator().next().getAuthority();

			// Generate JWT token
			String token = jwtProvider.generateToken(
				request.getUsername(),
				request.getCompanyCode(),
				role
			);

			// Build authentication response
			return new LoginResponseDto(
				token,
				"Bearer",
				request.getCompanyCode(),
				request.getUsername(),
				role
			);
		}finally {
			// Clear tenant context
			TenantContext.clear();
		}
	}

}