package com.multitenant.app.master.service;

/**
 * TenantSchemaService
 *
 * Initializes tenant-specific database schema.
 *
 * Responsible for creating application tables
 * required for tenant startup.
 */
public interface TenantSchemaService {

	/**
	 * Creates application tables inside tenant database.
	 *
	 * @param databaseName tenant database name
	 */
	void createTenantSchema(String databaseName);

}