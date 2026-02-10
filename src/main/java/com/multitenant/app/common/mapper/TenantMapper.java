package com.multitenant.app.common.mapper;

import com.multitenant.app.master.dto.request.TenantRequestDto;
import com.multitenant.app.master.dto.response.TenantResponseDto;
import com.multitenant.app.master.model.TenantModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * TenantMapper
 *
 * Maps tenant master entity and DTO objects used
 * for tenant registration and management operations.
 */
@Component
@AllArgsConstructor
public class TenantMapper {

	// ModelMapper instance used for object mapping
	private final ModelMapper modelMapper;

	/**
	 * Maps Tenant entity to response DTO.
	 */
	public TenantResponseDto toDto(TenantModel tenant) {
		if (tenant == null) return null;
		return modelMapper.map(tenant, TenantResponseDto.class);
	}

	/**
	 * Maps request DTO to Tenant entity.
	 */
	public TenantModel toEntity(TenantRequestDto dto) {
		if (dto == null) return null;
		return modelMapper.map(dto, TenantModel.class);
	}

}