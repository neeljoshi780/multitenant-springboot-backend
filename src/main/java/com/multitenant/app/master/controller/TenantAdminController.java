package com.multitenant.app.master.controller;

import com.multitenant.app.common.constants.api.path.TenantAdminApiPath;
import com.multitenant.app.master.dto.request.TenantRegisterRequestDto;
import com.multitenant.app.master.service.TenantOnboardingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TenantAdminController
 *
 * Handles tenant onboarding and registration APIs
 * for the multi-tenant SaaS platform.
 *
 * Allows new organizations to register and
 * provisions required tenant resources.
 */
@RestController
@RequestMapping(TenantAdminApiPath.BASE)
@RequiredArgsConstructor
public class TenantAdminController {

	// Service responsible for tenant onboarding workflow
	private final TenantOnboardingService tenantOnboardingService;

	/**
	 * Registers a new tenant and provisions required
	 * tenant-specific resources.
	 *
	 * @param request tenant registration request payload
	 * @return HTTP 201 (Created) on successful registration
	 */
	@PostMapping(TenantAdminApiPath.REGISTER)
	public ResponseEntity<Void> RegisterTenant(@Valid @RequestBody TenantRegisterRequestDto request) {
		tenantOnboardingService.onboardTenant(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}