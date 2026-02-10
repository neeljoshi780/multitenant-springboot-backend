package com.multitenant.app.master.service.impl;

import com.multitenant.app.master.service.TenantPhysicalDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * TenantPhysicalDbServiceImpl
 *
 * Concrete implementation for physical tenant
 * database provisioning.
 */
@Service
@RequiredArgsConstructor
public class TenantPhysicalDbServiceImpl implements TenantPhysicalDbService {

	/* Executes low-level database creation commands */
	private final JdbcTemplate jdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createDatabase(String databaseName) {
		// Execute database creation command
		jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS " + databaseName);
	}

}