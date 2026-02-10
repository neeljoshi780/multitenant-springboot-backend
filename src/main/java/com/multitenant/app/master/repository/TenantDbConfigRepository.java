package com.multitenant.app.master.repository;

import com.multitenant.app.master.model.TenantDbConfigModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * TenantDbConfigRepository
 *
 * Repository interface used to manage tenant database
 * configuration records stored in the master database.
 *
 * Supports dynamic datasource routing and tenant
 * configuration lookup operations.
 */
@Repository
public interface TenantDbConfigRepository extends JpaRepository<TenantDbConfigModel, Long> {

	/**
	 * Retrieves tenant database configuration using tenant ID.
	 *
	 * @param tenantId unique tenant identifier
	 * @return optional tenant database configuration
	 */
	Optional<TenantDbConfigModel> findByTenantId(UUID tenantId);

	/**
	 * Retrieves tenant database configuration using database name.
	 *
	 * @param dbName physical database name
	 * @return optional tenant database configuration
	 */
	Optional<TenantDbConfigModel> findByDbName(String dbName);

}