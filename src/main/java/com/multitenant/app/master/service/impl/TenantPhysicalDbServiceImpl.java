package com.multitenant.app.master.service.impl;

import com.multitenant.app.master.service.TenantPhysicalDbService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * TenantPhysicalDbServiceImpl
 *
 * Concrete implementation for physical tenant
 * database provisioning.
 */
@Service
public class TenantPhysicalDbServiceImpl implements TenantPhysicalDbService {

	/* Executes low-level database creation commands */
	private final JdbcTemplate jdbcTemplate;

	public TenantPhysicalDbServiceImpl(@Qualifier("masterJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createDatabase(String databaseName) {
		// Execute database creation command
		jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS " + databaseName);
	}

}