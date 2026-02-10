package com.multitenant.app.master.service;

import com.multitenant.app.master.dto.request.TenantDbConfigRequestDto;
import com.multitenant.app.master.dto.response.TenantDbConfigResponseDto;

import java.util.UUID;

/**
 * TenantDbConfigService
 *
 * Manages tenant database connection configuration.
 *
 * Stores and provides database metadata required
 * for dynamic routing and tenant isolation.
 */
public interface TenantDbConfigService {

	/**
	 * Saves tenant database configuration.
	 *
	 * @param requestDto database configuration data
	 * @param tenantId tenant unique identifier
	 */
	void saveDbConfig(TenantDbConfigRequestDto requestDto, UUID tenantId);

	/**
	 * Retrieves tenant database configuration by tenant ID.
	 *
	 * @param tenantId tenant unique identifier
	 * @return tenant database configuration
	 */
	TenantDbConfigResponseDto getDbConfigByTenantId(UUID tenantId);

	/**
	 * Retrieves tenant database configuration by database name.
	 *
	 * @param dbName tenant database name
	 * @return tenant database configuration
	 */
	TenantDbConfigResponseDto getDbConfigByDbName(String dbName);

}