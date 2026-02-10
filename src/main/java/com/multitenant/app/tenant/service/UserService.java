package com.multitenant.app.tenant.service;

import com.multitenant.app.common.response.PageResponseDto;
import com.multitenant.app.tenant.dto.request.UserCreateRequestDto;
import com.multitenant.app.tenant.dto.request.UserUpdateRequestDto;
import com.multitenant.app.tenant.dto.response.UserResponseDto;

/**
 * UserService
 *
 * Service interface for managing tenant users.
 *
 * Defines core operations such as creation, update,
 * deletion, retrieval, and paginated listing of users
 * inside a tenant database.
 *
 * Implementations handle validation and persistence logic.
 */
public interface UserService {

	/**
	 * Creates a new tenant user.
	 *
	 * @param requestDto user creation request data
	 * @return created user response
	 */
	UserResponseDto createUser(UserCreateRequestDto requestDto);

	/**
	 * Updates existing tenant user.
	 *
	 * @param requestDto updated user data
	 * @return updated user response
	 */
	UserResponseDto updateUser(UserUpdateRequestDto requestDto);

	/**
	 * Deletes tenant user by ID.
	 *
	 * @param id user unique identifier
	 */
	void deleteUser(Long id);

	/**
	 * Retrieves tenant user by ID.
	 *
	 * @param id user unique identifier
	 * @return user response data
	 */
	UserResponseDto getUser(Long id);

	/**
	 * Retrieves paginated list of tenant users.
	 *
	 * @param pageNo page number
	 * @param pageSize page size
	 * @param sortBy sort field
	 * @param sortDir sort direction
	 * @return paginated user response list
	 */
	PageResponseDto<UserResponseDto> getUsers(Long pageNo, Long pageSize, String sortBy, String sortDir);

}