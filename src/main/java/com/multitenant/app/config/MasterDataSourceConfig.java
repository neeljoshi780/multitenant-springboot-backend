package com.multitenant.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * MasterDataSourceConfig
 *
 * Configures MASTER database connection.
 *
 * MASTER database is used for tenant registry,
 * tenant lookup, and onboarding operations.
 */
@Configuration
public class MasterDataSourceConfig {

	/**
	 * Loads MASTER datasource configuration properties.
	 */
	@Bean
	@ConfigurationProperties("spring.datasource.master")
	public DataSourceProperties masterDataSourceProperties() {
		return new DataSourceProperties();
	}

	/**
	 * Creates MASTER database DataSource bean.
	 */
	@Bean(name = "masterDataSource")
	public DataSource masterDataSource() {
		return masterDataSourceProperties()
			.initializeDataSourceBuilder()
			.build();
	}

	/**
	 * Creates JdbcTemplate for MASTER database operations.
	 */
	@Bean(name = "masterJdbcTemplate")
	public JdbcTemplate masterJdbcTemplate(@Qualifier("masterDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}