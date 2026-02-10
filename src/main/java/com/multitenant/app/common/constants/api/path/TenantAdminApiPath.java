package com.multitenant.app.common.constants.api.path;

/**
 * TenantAdminApiPath
 *
 * Defines REST endpoint paths related to
 * tenant administration and onboarding operations.
 *
 * These APIs are used for registering new tenants,
 * managing tenant status, and retrieving tenant metadata.
 */
public final class TenantAdminApiPath {

	/* Base path for tenant admin APIs */
	public static final String BASE = ApiBasePath.API + "/admin/tenant";

	/* Tenant registration endpoint */
	public static final String REGISTER = "/register";

	private TenantAdminApiPath() {} // Prevent instantiation

}