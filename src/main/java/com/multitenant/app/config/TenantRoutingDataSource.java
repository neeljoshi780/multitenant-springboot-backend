package com.multitenant.app.config;

import com.multitenant.app.context.TenantContext;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * TenantRoutingDataSource
 *
 * Dynamically routes database connections
 * between MASTER and TENANT databases.
 *
 * Uses TenantContext to determine which
 * tenant database should be used
 * for the current request.
 */
@Setter
public class TenantRoutingDataSource extends AbstractRoutingDataSource {

	/* Resolves tenant database to actual DataSource */
	private TenantDataSourceResolver resolver;

	/**
	 * Returns current tenant lookup key.
	 *
	 * This value is used to identify
	 * which tenant database should be selected.
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return TenantContext.getTenantDb();
	}

	/**
	 * Resolves and returns tenant DataSource.
	 *
	 * Falls back to MASTER datasource when
	 * tenant context is not available.
	 */
	@Override
	@NonNull
	protected DataSource determineTargetDataSource() {
		Object lookupKey = determineCurrentLookupKey();
		// Resolve tenant datasource if tenant context exists
		if (lookupKey != null && resolver != null) {
			DataSource ds = resolver.resolveDataSource(lookupKey.toString());
			if (ds != null)
				return ds;
		}
		// Fallback to master datasource
		return super.determineTargetDataSource();
	}

}