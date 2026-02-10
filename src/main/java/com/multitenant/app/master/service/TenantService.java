package com.multitenant.app.master.service;

import com.multitenant.app.master.dto.request.TenantRequestDto;
import com.multitenant.app.master.dto.response.TenantResponseDto;

/**
 * TenantService
 *
 * Manages tenant (company) master data stored
 * in the control (master) database.
 *
 * Responsible for:
 * - Creating tenant records during signup
 * - Retrieving tenant details for routing and login
 * - Enforcing uniqueness constraints
 */
public interface TenantService {

	/**
	 * Creates a new tenant (company) record.
	 *
	 * @param requestDto tenant registration data
	 * @return created tenant response
	 */
	TenantResponseDto createTenant(TenantRequestDto requestDto);

	/**
	 * Retrieves tenant information using company code.
	 *
	 * @param companyCode unique tenant identifier
	 * @return tenant response data
	 */
	TenantResponseDto getTenantByCompanyCode(String companyCode);

}