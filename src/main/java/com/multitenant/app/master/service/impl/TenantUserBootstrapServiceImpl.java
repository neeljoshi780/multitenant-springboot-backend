package com.multitenant.app.master.service.impl;

import com.multitenant.app.context.TenantContext;
import com.multitenant.app.master.service.TenantUserBootstrapService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * TenantUserBootstrapServiceImpl
 *
 * Concrete implementation for creating
 * initial tenant admin user.
 */
@Service
public class TenantUserBootstrapServiceImpl implements TenantUserBootstrapService {

	/* Executes low-level database creation commands */
	private final JdbcTemplate jdbcTemplate;

	public TenantUserBootstrapServiceImpl(@Qualifier("tenantJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createAdminUser(String databaseName, String email, String username, String encodedPassword) {
		try {
			// Set tenant context to switch routing
			TenantContext.setTenantDb(databaseName);

			// Insert admin user record
			jdbcTemplate.update("""
			INSERT INTO users
			(email, username, password, role, status, created_at, updated_at)
			VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
			""", email, username, encodedPassword, "ADMIN", "ACTIVE");
		} finally {
			// Clear tenant context
			TenantContext.clear();
		}
	}

}