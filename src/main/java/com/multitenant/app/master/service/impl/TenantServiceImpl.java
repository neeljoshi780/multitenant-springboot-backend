package com.multitenant.app.master.service.impl;

import com.multitenant.app.common.exception.DuplicateResourceException;
import com.multitenant.app.common.exception.ResourceNotFoundException;
import com.multitenant.app.common.mapper.TenantMapper;
import com.multitenant.app.master.dto.request.TenantRequestDto;
import com.multitenant.app.master.dto.response.TenantResponseDto;
import com.multitenant.app.master.model.TenantModel;
import com.multitenant.app.master.repository.TenantRepository;
import com.multitenant.app.master.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TenantServiceImpl
 *
 * Concrete implementation of tenant master
 * data management operations.
 */
@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

	/* Repository for tenant database operations */
	private final TenantRepository tenantRepository;

	/* Maps tenant entity and DTO objects */
	private final TenantMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TenantResponseDto createTenant(TenantRequestDto requestDto) {
		// Validate company code uniqueness
		if (tenantRepository.existsByCompanyCode(requestDto.getCompanyCode())) {
			throw new DuplicateResourceException("Company code already exists", "companyCode");
		}

		// Validate company email uniqueness
		if (tenantRepository.existsByCompanyEmail(requestDto.getCompanyEmail())) {
			throw new DuplicateResourceException("Company email already exists", "companyEmail");
		}

		// Convert DTO to entity
		TenantModel tenant = mapper.toEntity(requestDto);
		// Persist tenant record and return response
		return mapper.toDto(tenantRepository.save(tenant));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TenantResponseDto getTenantByCompanyCode(String companyCode) {
		// Fetch tenant record by company code
		TenantModel tenant = tenantRepository.findByCompanyCode(companyCode)
			.orElseThrow(() -> new ResourceNotFoundException("Invalid Company Code"));
		// Convert entity to response DTO
		return mapper.toDto(tenant);
	}

}