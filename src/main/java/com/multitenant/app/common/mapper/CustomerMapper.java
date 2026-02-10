package com.multitenant.app.common.mapper;

import com.multitenant.app.tenant.dto.request.CustomerRequestDto;
import com.multitenant.app.tenant.dto.response.CustomerResponseDto;
import com.multitenant.app.tenant.enums.Gender;
import com.multitenant.app.tenant.model.CustomerModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * CustomerMapper
 *
 * Maps Customer entity and DTO objects while handling
 * custom field conversions such as gender enum mapping.
 */
@Component
public class CustomerMapper {

	// ModelMapper instance used for object mapping
	private final ModelMapper modelMapper;

	public CustomerMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		configureMappings();
	}

	/**
	 * Configures custom mapping rules for Customer domain.
	 *
	 * Gender field mapping is skipped to allow manual
	 * enum conversion and avoid incorrect automatic mapping.
	 */
	private void configureMappings() {
		modelMapper.typeMap(CustomerRequestDto.class, CustomerModel.class)
			.addMappings(m -> m.skip(CustomerModel::setGender));
	}

	/**
	 * Maps Customer entity to response DTO.
	 */
	public CustomerResponseDto toDto(CustomerModel customer) {
		if (customer == null) return null;
		CustomerResponseDto dto = modelMapper.map(customer, CustomerResponseDto.class);
		dto.setGender(Gender.fromValue(customer.getGender()).name());
		return dto;
	}

	/**
	 * Maps request DTO to Customer entity.
	 */
	public CustomerModel toEntity(CustomerRequestDto dto) {
		if (dto == null) return null;
		CustomerModel entity = modelMapper.map(dto, CustomerModel.class);

		// Convert gender string → byte
		Byte gender = Gender.fromString(dto.getGender());
		entity.setGender(gender);
		return entity;
	}

}