package com.multitenant.app.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

/**
 * TenantJpaConfig
 *
 * Configures JPA infrastructure for TENANT databases.
 *
 * Uses routing datasource to dynamically connect
 * to the correct tenant database based on request context.
 *
 * Marked as @Primary to ensure tenant repositories
 * use tenant database by default instead of master database.
 */
@Configuration
@EnableJpaRepositories(
	basePackages = "com.multitenant.app.tenant.repository",
	entityManagerFactoryRef = "tenantEntityManagerFactory",
	transactionManagerRef = "tenantTransactionManager"
)
public class TenantJpaConfig {

	/**
	 * Creates EntityManagerFactory for tenant databases.
	 *
	 * Uses TenantRoutingDataSource to support
	 * runtime tenant database switching.
	 */
	@Primary
	@Bean(name = "tenantEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(
		EntityManagerFactoryBuilder builder,
		@Qualifier("tenantRoutingDataSource") DataSource dataSource) {

		return builder
			.dataSource(dataSource)
			.packages("com.multitenant.app.tenant.model")
			.persistenceUnit("tenantPU")
			.build();
	}

	/**
	 * Transaction manager for tenant database operations.
	 *
	 * Handles transactions for all tenant repositories.
	 */
	@Primary
	@Bean(name = "tenantTransactionManager")
	public JpaTransactionManager tenantTransactionManager(@Qualifier("tenantEntityManagerFactory") EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}