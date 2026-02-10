package com.multitenant.app.master.service.impl;

import com.multitenant.app.master.dto.response.TenantDbConfigResponseDto;
import com.multitenant.app.master.service.TenantDbConfigService;
import com.multitenant.app.master.service.TenantLookupService;
import com.multitenant.app.master.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TenantLookupServiceImpl
 *
 * Implements tenant database resolution logic using
 * master tenant registry and database configuration tables.
 *
 * Used by filters and routing components to dynamically
 * select the appropriate tenant database connection.
 */
@Service
@RequiredArgsConstructor
public class TenantLookupServiceImpl implements TenantLookupService {

	/* Manages tenant master records */
	private final TenantService tenantService;

	/* Persists tenant database configuration */
	private final TenantDbConfigService tenantDbConfigService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TenantDbConfigResponseDto findTenantDatabase(String companyCode) {
		var tenant = tenantService.getTenantByCompanyCode(companyCode);
		return tenantDbConfigService.getDbConfigByTenantId(tenant.getId());
	}

}