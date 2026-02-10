package com.multitenant.app.config;

import com.multitenant.app.master.service.TenantDbConfigService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * TenantDataSourceResolver
 *
 * Resolves tenant database DataSource using
 * tenant configuration provided by service layer.
 *
 * Uses in-memory cache to reuse connection pools
 * and avoid repeated DataSource creation.
 */
@Component
@RequiredArgsConstructor
public class TenantDataSourceResolver {

	/* Service used to fetch tenant database configuration */
	private final TenantDbConfigService tenantDbConfigService;

	/* Cache to store DataSource per tenant database */
	private final Map<String, DataSource> cache = new ConcurrentHashMap<>();

	/**
	 * Resolves tenant DataSource by database name.
	 *
	 * Uses cache if available, otherwise creates
	 * a new DataSource and stores it.
	 */
	public DataSource resolveDataSource(String dbName) {
		if (dbName == null || dbName.isBlank())
			return null;
		return cache.computeIfAbsent(dbName, this::createDataSourceForDb);
	}

	/**
	 * Creates DataSource using tenant database configuration.
	 *
	 * Called only once per tenant and cached for reuse.
	 */
	private DataSource createDataSourceForDb(String dbName) {
		// Fetch tenant DB configuration via service layer
		var config = tenantDbConfigService.getDbConfigByDbName(dbName);

		// Configure Hikari connection pool
		HikariConfig hikari = new HikariConfig();
		hikari.setJdbcUrl(String.format(
			"jdbc:mysql://%s:%d/%s?useSSL=false&serverTimezone=UTC",
			config.getDbHost(),
			config.getDbPort() != null ? config.getDbPort() : 3306,
			config.getDbName()
		));

		hikari.setUsername(config.getDbUsername());
		hikari.setPassword(config.getDbPassword());
		hikari.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikari.setMaximumPoolSize(5);
		hikari.setMinimumIdle(1);
		return new HikariDataSource(hikari);
	}

}