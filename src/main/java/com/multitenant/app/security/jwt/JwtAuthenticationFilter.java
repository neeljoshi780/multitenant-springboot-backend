package com.multitenant.app.security.jwt;

import com.multitenant.app.context.TenantContext;
import com.multitenant.app.master.service.TenantLookupService;
import com.multitenant.app.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter
 *
 * Validates incoming JWT tokens and restores tenant context
 * for correct multi-tenant database routing.
 *
 * Responsibilities:
 * - Extract and validate JWT token
 * - Resolve tenant database using company code
 * - Set TenantContext for routing datasource
 * - Authenticate user and set SecurityContext
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/* JWT utility for token validation and claim extraction */
	private final JwtTokenProvider jwtProvider;

	/* Resolves tenant database configuration using company code */
	private final TenantLookupService tenantLookupService;

	/* Loads authenticated user details from tenant database */
	private final CustomUserDetailsService userDetailsService;

	/**
	 * {@inheritDoc}
	 *
	 * Security Flow:
	 * 1. Extract JWT from Authorization header
	 * 2. Validate token signature and expiration
	 * 3. Resolve tenant database using company code
	 * 4. Set tenant context for routing datasource
	 * 5. Load user details and set authentication context
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
		throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		// Process JWT only if Authorization header is present
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);

			if (jwtProvider.validateToken(token)) {
				String username = jwtProvider.getUsername(token);
				String companyCode = jwtProvider.getTenantCode(token);

				try {
					// Resolve tenant database and set routing context
					var dbConfig = tenantLookupService.findTenantDatabase(companyCode);
					TenantContext.setTenantDb(dbConfig.getDbName());

					// Load authenticated user from tenant database
					var userDetails = userDetailsService.loadUserByUsername(username);

					// Build authentication token and set security context
					var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				} catch (Exception e) {
					// Ignore exception to allow Spring Security to handle unauthorized response
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}