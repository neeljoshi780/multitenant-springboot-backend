package com.multitenant.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * DataSourceConfig
 *
 * Configures routing DataSource for multi-tenant
 * database switching.
 *
 * Routes tenant repositories to tenant databases
 * and falls back to MASTER database when tenant
 * context is not available.
 */
@Configuration
public class DataSourceConfig {

	/**
	 * Creates primary routing DataSource used for
	 * tenant database resolution.
	 */
	@Bean(name = "tenantRoutingDataSource")
	@Primary
	public DataSource routingDataSource(
		@Qualifier("masterDataSource") DataSource masterDataSource,
		@Lazy TenantDataSourceResolver resolver) {

		// Create routing datasource instance
		TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();

		// Set MASTER database as default fallback datasource
		routingDataSource.setDefaultTargetDataSource(masterDataSource);

		// Initialize empty target map (resolved dynamically)
		routingDataSource.setTargetDataSources(new HashMap<>());
		// Set tenant datasource resolver
		routingDataSource.setResolver(resolver);
		// Finalize datasource initialization
		routingDataSource.afterPropertiesSet();
		// Return routing datasource bean
		return routingDataSource;
	}

}