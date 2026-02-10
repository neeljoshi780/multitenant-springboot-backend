package com.multitenant.app.master.service.impl;

import com.multitenant.app.master.service.TenantUserBootstrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * TenantUserBootstrapServiceImpl
 *
 * Concrete implementation for creating
 * initial tenant admin user.
 */
@Service
@RequiredArgsConstructor
public class TenantUserBootstrapServiceImpl implements TenantUserBootstrapService {

	/* Executes user creation SQL statements */
	private final JdbcTemplate jdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createAdminUser(String databaseName, String email, String username, String encodedPassword) {
		// Switch to tenant database
		jdbcTemplate.execute("USE " + databaseName);

		// Insert admin user record
		jdbcTemplate.update("""
			INSERT INTO users
			(email, username, password, role, status, created_at, updated_at)
			VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
		""",email, username, encodedPassword, "ADMIN", "ACTIVE"
		);
	}

}