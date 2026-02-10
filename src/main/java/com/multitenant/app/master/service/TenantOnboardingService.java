package com.multitenant.app.master.service;

import com.multitenant.app.master.dto.request.TenantRegisterRequestDto;

/**
 * TenantOnboardingService
 *
 * Handles complete tenant (company) signup workflow.
 *
 * This service is responsible for coordinating:
 * - Tenant master record creation
 * - Physical tenant database provisioning
 * - Tenant schema initialization
 * - admin user creation
 *
 * Acts as the main entry point for SaaS tenant onboarding.
 */
public interface TenantOnboardingService {

	/**
	 * Executes tenant registration and provisioning process.
	 *
	 * @param request tenant registration request payload
	 */
	void onboardTenant(TenantRegisterRequestDto request);

}