package com.multitenant.app.master.service.impl;

import com.multitenant.app.master.dto.request.TenantDbConfigRequestDto;
import com.multitenant.app.master.dto.request.TenantRegisterRequestDto;
import com.multitenant.app.master.dto.request.TenantRequestDto;
import com.multitenant.app.master.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TenantOnboardingServiceImpl
 *
 * Concrete implementation of tenant onboarding workflow.
 *
 * Coordinates tenant creation, database provisioning,
 * schema setup, and admin user initialization.
 */
@Service
@RequiredArgsConstructor
public class TenantOnboardingServiceImpl implements TenantOnboardingService {

	/* Manages tenant master records */
	private final TenantService tenantService;

	/* Persists tenant database configuration */
	private final TenantDbConfigService tenantDbService;

	/* Creates physical tenant databases */
	private final TenantPhysicalDbService physicalDbService;

	/* Initializes tenant database schema */
	private final TenantSchemaService schemaService;

	/* Creates default tenant admin user */
	private final TenantUserBootstrapService userBootstrapService;

	/* Encrypts admin passwords */
	private final PasswordEncoder passwordEncoder;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional("masterTransactionManager")
	public void onboardTenant(TenantRegisterRequestDto request) {
		// Build tenant creation request
		TenantRequestDto tenantRequest = TenantRequestDto.builder()
			.companyCode(request.getCompanyCode())
			.companyName(request.getCompanyName())
			.companyEmail(request.getCompanyEmail())
			.build();

		// Create tenant master record
		var tenantResponseDto = tenantService.createTenant(tenantRequest);

		// Generate tenant database name
		String dbName = "tenant_" + tenantResponseDto.getCompanyCode().toLowerCase();

		// Create physical tenant database
		physicalDbService.createDatabase(dbName);

		// Store tenant database configuration
		TenantDbConfigRequestDto dbConfig = TenantDbConfigRequestDto.builder()
			.dbName(dbName)
			.dbHost("localhost")
			.dbPort(3306)
			.dbUsername("root")
			.dbPassword("455445")
			.build();
		tenantDbService.saveDbConfig(dbConfig, tenantResponseDto.getId());

		// Initialize tenant schema
		schemaService.createTenantSchema(dbName);

		// Encrypt admin password
		String encodedPassword = passwordEncoder.encode(request.getAdminPassword());

		// Create tenant admin user
		userBootstrapService.createAdminUser(dbName, request.getAdminEmail(), request.getAdminUsername(), encodedPassword);
	}

}