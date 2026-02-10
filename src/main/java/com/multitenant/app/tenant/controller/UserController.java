package com.multitenant.app.tenant.controller;

import com.multitenant.app.common.constants.api.path.ApiBasePath;
import com.multitenant.app.common.constants.api.path.UserApiPath;
import com.multitenant.app.common.response.PageResponseDto;
import com.multitenant.app.tenant.dto.request.UserCreateRequestDto;
import com.multitenant.app.tenant.dto.request.UserUpdateRequestDto;
import com.multitenant.app.tenant.dto.response.UserResponseDto;
import com.multitenant.app.tenant.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 *
 * REST controller for tenant user management APIs.
 *
 * Provides CRUD and paginated listing operations
 * for application users within tenant database context.
 */
@RestController
@RequestMapping(UserApiPath.BASE)
@RequiredArgsConstructor
public class UserController {

	/* Handles tenant user business operations */
	private final UserService userService;

	/**
	 * Creates a new tenant user.
	 */
	@PostMapping
	public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto requestDto) {
		UserResponseDto createdUser = userService.createUser(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	/**
	 * Fetches user details by ID.
	 */
	@GetMapping(ApiBasePath.ID)
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
		UserResponseDto user = userService.getUser(id);
		return ResponseEntity.ok(user);
	}

	/**
	 * Returns paginated list of tenant users.
	 */
	@GetMapping
	public ResponseEntity<PageResponseDto<UserResponseDto>> getUsers(
		@RequestParam(defaultValue = "0") Long pageNo,
		@RequestParam(defaultValue = "10") Long pageSize,
		@RequestParam(defaultValue = "id") String sortBy,
		@RequestParam(defaultValue = "asc") String sortDir) {

		PageResponseDto<UserResponseDto> response = userService.getUsers(pageNo, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(response);
	}

	/**
	 * Updates existing user data.
	 */
	@PutMapping
	public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserUpdateRequestDto requestDto) {
		UserResponseDto updatedUser = userService.updateUser(requestDto);
		return ResponseEntity.ok(updatedUser);
	}

	/**
	 * Deletes user by ID.
	 */
	@DeleteMapping(ApiBasePath.ID)
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

}