package com.multitenant.app.master.service;

/**
 * TenantPhysicalDbService
 *
 * Responsible for provisioning physical tenant databases.
 *
 * This service creates isolated databases for each tenant
 * during the onboarding process.
 */
public interface TenantPhysicalDbService {

	/**
	 * Creates a physical database for a tenant.
	 *
	 * @param databaseName generated tenant database name
	 */
	void createDatabase(String databaseName);

}