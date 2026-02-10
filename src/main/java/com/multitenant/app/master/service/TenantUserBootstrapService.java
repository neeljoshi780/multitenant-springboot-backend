package com.multitenant.app.master.service;

/**
 * TenantUserBootstrapService
 *
 * Creates initial user accounts for newly
 * onboarded tenants.
 *
 * Used to bootstrap the default admin user
 * during tenant registration.
 */
public interface TenantUserBootstrapService {

	/**
	 * Creates default admin user inside tenant database.
	 *
	 * @param databaseName tenant database name
	 * @param email admin email address
	 * @param username admin login username
	 * @param encodedPassword encrypted password
	 */
	void createAdminUser(String databaseName, String email, String username, String encodedPassword);

}