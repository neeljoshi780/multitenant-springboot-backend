package com.multitenant.app.context;

/**
 * TenantContext
 *
 * Stores tenant-specific database identifier for the
 * current request thread using ThreadLocal.
 *
 * This context enables dynamic datasource routing and
 * ensures tenant data isolation during request processing.
 */
public final class TenantContext {

	// Thread-local storage for current tenant database name
	private static final ThreadLocal<String> CURRENT_TENANT_DB = new ThreadLocal<>();

	private TenantContext() {} // Prevent instantiation

	/**
	 * Sets tenant database name for the current request.
	 *
	 * @param dbName resolved tenant database name
	 */
	public static void setTenantDb(String dbName) {
		CURRENT_TENANT_DB.set(dbName);
	}

	/**
	 * Returns tenant database name for the current request.
	 *
	 * @return tenant database name
	 */
	public static String getTenantDb() {
		return CURRENT_TENANT_DB.get();
	}

	/**
	 * Clears tenant context after request completion.
	 *
	 * This is required to prevent memory leaks and
	 * cross-tenant data access issues.
	 */
	public static void clear() {
		CURRENT_TENANT_DB.remove();
	}

}