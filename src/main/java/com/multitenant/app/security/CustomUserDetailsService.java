package com.multitenant.app.security;

import com.multitenant.app.context.TenantContext;
import com.multitenant.app.tenant.model.UserModel;
import com.multitenant.app.tenant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService
 *
 * Loads tenant-specific user credentials for Spring Security authentication.
 *
 * Uses TenantContext with routing datasource to ensure
 * user data is fetched from the correct tenant database.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	/* Repository for accessing tenant user records */
	private final UserRepository userRepository;

	/**
	 * {@inheritDoc}
	 *
	 * Authentication Flow:
	 * - Validates tenant context availability
	 * - Fetches user from tenant database
	 * - Maps entity to Spring Security UserDetails
	 */
	@Override
	@NonNull
	public UserDetails loadUserByUsername(@NonNull String username) {
		// Ensure tenant context is initialized before querying tenant DB
		if (TenantContext.getTenantDb() == null) {
			throw new UsernameNotFoundException("Tenant context not initialized");
		}

		// Fetch user from tenant database
		UserModel user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

		// Convert tenant user entity to Spring Security compatible user object
		return User.builder()
			.username(user.getUsername())
			.password(user.getPassword())
			.roles(user.getRole())
			.build();
	}

}