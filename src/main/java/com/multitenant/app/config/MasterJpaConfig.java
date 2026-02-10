package com.multitenant.app.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import javax.sql.DataSource;

/**
 * MasterJpaConfig
 *
 * Configures JPA infrastructure for MASTER database.
 *
 * Binds master repositories to master datasource
 * and manages tenant registry persistence.
 */
@Configuration
@EnableJpaRepositories(
	basePackages = "com.multitenant.app.master.repository",
	entityManagerFactoryRef = "masterEntityManagerFactory",
	transactionManagerRef = "masterTransactionManager"
)
public class MasterJpaConfig {

	/**
	 * Creates EntityManagerFactory for MASTER database.
	 */
	@Bean(name = "masterEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("masterDataSource") DataSource dataSource) {
		// Configure master datasource and entity package
		return builder
			.dataSource(dataSource)
			.packages("com.multitenant.app.master.model")
			.persistenceUnit("masterPU")
			.build();
	}

	/**
	 * Creates transaction manager for MASTER database.
	 */
	@Bean(name = "masterTransactionManager")
	public JpaTransactionManager masterTransactionManager(@Qualifier("masterEntityManagerFactory") EntityManagerFactory emf) {
		// Bind transaction manager to master entity manager
		return new JpaTransactionManager(emf);
	}

}