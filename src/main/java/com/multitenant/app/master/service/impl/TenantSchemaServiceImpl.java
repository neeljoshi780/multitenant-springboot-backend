package com.multitenant.app.master.service.impl;

import com.multitenant.app.context.TenantContext;
import com.multitenant.app.master.service.TenantSchemaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * TenantSchemaServiceImpl
 *
 * Concrete implementation for tenant
 * database schema initialization.
 */
@Service
public class TenantSchemaServiceImpl implements TenantSchemaService {

	/* Executes low-level database creation commands */
	private final JdbcTemplate jdbcTemplate;

	public TenantSchemaServiceImpl(@Qualifier("tenantJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createTenantSchema(String databaseName) {
		try {
			// Set tenant context to switch routing
			TenantContext.setTenantDb(databaseName);

			// Create users table
			jdbcTemplate.execute("""
			CREATE TABLE IF NOT EXISTS users (
				id BIGINT PRIMARY KEY AUTO_INCREMENT,
				email VARCHAR(150) UNIQUE NOT NULL,
				username VARCHAR(50) UNIQUE NOT NULL,
				password TEXT NOT NULL,
				role VARCHAR(30) NOT NULL,
				status VARCHAR(20) NOT NULL,
				created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
				updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
			)
		""");

			// Create customers table
			jdbcTemplate.execute("""
			CREATE TABLE IF NOT EXISTS customers (
				id BIGINT AUTO_INCREMENT PRIMARY KEY,
				first_name VARCHAR(50) NOT NULL,
				last_name VARCHAR(50) NOT NULL,
				date_of_birth DATE,
				age TINYINT UNSIGNED,
				gender TINYINT UNSIGNED,
				mobile VARCHAR(20) NOT NULL UNIQUE,
				email VARCHAR(150) NOT NULL UNIQUE,
				address1 VARCHAR(255) NOT NULL,
				address2 VARCHAR(255),
				created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
				updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
			)
		""");
		} finally {
			// Clear tenant context to avoid data contamination
			TenantContext.clear();
		}
	}

}