package com.multitenant.app.common.mapper;

import com.multitenant.app.tenant.dto.request.UserCreateRequestDto;
import com.multitenant.app.tenant.dto.response.UserResponseDto;
import com.multitenant.app.tenant.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * UserMapper
 *
 * Maps tenant user entity and DTO objects
 * used for user management operations.
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

	// ModelMapper instance used for object mapping
	private final ModelMapper modelMapper;

	/**
	 * Maps User entity to response DTO.
	 */
	public UserResponseDto toDto(UserModel user) {
		if (user == null)
			return null;
		return modelMapper.map(user, UserResponseDto.class);
	}

	/**
	 * Maps request DTO to User entity.
	 */
	public UserModel toEntity(UserCreateRequestDto dto) {
		if (dto == null)
			return null;
		return modelMapper.map(dto, UserModel.class);
	}

}