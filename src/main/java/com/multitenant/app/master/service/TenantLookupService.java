package com.multitenant.app.master.service;

import com.multitenant.app.common.exception.ResourceNotFoundException;
import com.multitenant.app.master.dto.response.TenantDbConfigResponseDto;

/**
 * TenantLookupService
 *
 * Service contract responsible for resolving tenant
 * database mapping information using tenant identifiers.
 *
 * Used by request filters and routing components to
 * dynamically determine the correct tenant database
 * during request processing.
 */
public interface TenantLookupService {

	/**
	 * Resolves tenant database configuration using company code.
	 *
	 * @param companyCode unique tenant company code
	 * @return tenant database configuration entity
	 *
	 * @throws ResourceNotFoundException if tenant or database configuration does not exist
	 */
	TenantDbConfigResponseDto findTenantDatabase(String companyCode);

}