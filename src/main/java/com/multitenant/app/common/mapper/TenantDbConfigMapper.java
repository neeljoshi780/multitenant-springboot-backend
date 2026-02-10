package com.multitenant.app.common.mapper;

import com.multitenant.app.master.dto.request.TenantDbConfigRequestDto;
import com.multitenant.app.master.dto.response.TenantDbConfigResponseDto;
import com.multitenant.app.master.model.TenantDbConfigModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * TenantDbConfigMapper
 *
 * Maps tenant database configuration entity and DTO objects
 * used for multi-tenant database setup and routing.
 */
@Component
@AllArgsConstructor
public class TenantDbConfigMapper {

	// ModelMapper instance used for object mapping
	private final ModelMapper modelMapper;

	/**
	 * Maps TenantDbConfig entity to response DTO.
	 */
	public TenantDbConfigResponseDto toDto(TenantDbConfigModel tenantDbConfigModel) {
		if (tenantDbConfigModel == null) return null;
		return modelMapper.map(tenantDbConfigModel, TenantDbConfigResponseDto.class);
	}

	/**
	 * Maps request DTO to TenantDbConfig entity.
	 */
	public TenantDbConfigModel toEntity(TenantDbConfigRequestDto dto) {
		if (dto == null) return null;
		return modelMapper.map(dto, TenantDbConfigModel.class);
	}

}