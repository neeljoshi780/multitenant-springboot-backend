package com.multitenant.app.master.service.impl;

import com.multitenant.app.common.exception.ResourceNotFoundException;
import com.multitenant.app.common.mapper.TenantDbConfigMapper;
import com.multitenant.app.master.dto.request.TenantDbConfigRequestDto;
import com.multitenant.app.master.dto.response.TenantDbConfigResponseDto;
import com.multitenant.app.master.model.TenantDbConfigModel;
import com.multitenant.app.master.model.TenantModel;
import com.multitenant.app.master.repository.TenantDbConfigRepository;
import com.multitenant.app.master.repository.TenantRepository;
import com.multitenant.app.master.service.TenantDbConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * TenantDbConfigServiceImpl
 *
 * Concrete implementation for managing tenant
 * database configuration records.
 */
@Service
@RequiredArgsConstructor
public class TenantDbConfigServiceImpl implements TenantDbConfigService {

	/* Repository for tenant database configuration records */
	private final TenantDbConfigRepository dbConfigRepository;

	/* Repository for tenant master records */
	private final TenantRepository tenantRepository;

	/* Maps database configuration entity and DTO */
	private final TenantDbConfigMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDbConfig(TenantDbConfigRequestDto requestDto, UUID tenantId) {
		// Convert DTO to entity
		TenantDbConfigModel entity = mapper.toEntity(requestDto);
		// Fetch tenant master record
		Optional<TenantModel> tenant = tenantRepository.findById(tenantId);
		// Associate tenant and persist configuration
		if(tenant.isPresent()){
			entity.setTenant(tenant.get());
			dbConfigRepository.save(entity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TenantDbConfigResponseDto getDbConfigByTenantId(UUID tenantId) {
		// Fetch database configuration for tenant
		TenantDbConfigModel config = dbConfigRepository.findByTenantId(tenantId)
			.orElseThrow(() -> new ResourceNotFoundException("Tenant Database Configuration Not Found"));
		// Convert entity to response DTO
		return mapper.toDto(config);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TenantDbConfigResponseDto getDbConfigByDbName(String dbName) {
		// Fetch configuration by database name
		TenantDbConfigModel config = dbConfigRepository.findByDbName(dbName)
			.orElseThrow(() -> new ResourceNotFoundException("Tenant Database Configuration Not Found"));
		// Convert entity to response DTO
		return mapper.toDto(config);
	}

}