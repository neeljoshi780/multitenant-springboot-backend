package com.multitenant.app.tenant.service.impl;

import com.multitenant.app.common.constants.PaginationConstants;
import com.multitenant.app.common.exception.DuplicateResourceException;
import com.multitenant.app.common.exception.ResourceNotFoundException;
import com.multitenant.app.common.mapper.UserMapper;
import com.multitenant.app.common.response.PageResponseDto;
import com.multitenant.app.tenant.dto.request.UserCreateRequestDto;
import com.multitenant.app.tenant.dto.request.UserUpdateRequestDto;
import com.multitenant.app.tenant.dto.response.UserResponseDto;
import com.multitenant.app.tenant.model.UserModel;
import com.multitenant.app.tenant.repository.UserRepository;
import com.multitenant.app.tenant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImpl
 *
 * Concrete implementation for tenant user operations.
 *
 * Handles validation rules, password encryption,
 * pagination logic, and repository coordination.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	/* Repository for tenant user persistence */
	private final UserRepository userRepository;

	// Object mapping
	private final ModelMapper modelMapper;

	/* Mapper for DTO and entity conversion */
	private final UserMapper mapper;

	/* Password encoder for secure storage */
	private final PasswordEncoder passwordEncoder;

	/**
	 * Builds pageable configuration with safe defaults.
	 *
	 * @param pageNo   page number
	 * @param pageSize page size
	 * @param sortBy   sorting field
	 * @param sortDir  sorting direction
	 * @return pageable object
	 */
	private Pageable buildPageable(Long pageNo, Long pageSize, String sortBy, String sortDir) {
		int page = (pageNo == null || pageNo < 0)
			? PaginationConstants.DEFAULT_PAGE_NO
			: pageNo.intValue();

		int size = (pageSize == null || pageSize <= 0)
			? PaginationConstants.DEFAULT_PAGE_SIZE
			: Math.min(pageSize.intValue(), PaginationConstants.MAX_PAGE_SIZE);

		Sort sort = sortDir.equalsIgnoreCase("desc")
			? Sort.by(sortBy).descending()
			: Sort.by(sortBy).ascending();
		return PageRequest.of(page, size, sort);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserResponseDto createUser(UserCreateRequestDto requestDto) {
		// Validate unique email
		if (userRepository.existsByEmail(requestDto.getEmail())) {
			throw new DuplicateResourceException("Email already registered", "email");
		}

		// Validate unique username
		if (userRepository.existsByUsername(requestDto.getUsername())) {
			throw new DuplicateResourceException("Username already exists", "username");
		}

		// Map request to entity
		UserModel entity = mapper.toEntity(requestDto);

		// Set default role and status
		entity.setRole("ADMIN");
		entity.setStatus("ACTIVE");

		// Encrypt password
		entity.setPassword(passwordEncoder.encode(requestDto.getPassword()));

		// Persist user
		UserModel saved = userRepository.save(entity);
		return mapper.toDto(saved);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserResponseDto updateUser(UserUpdateRequestDto requestDto) {
		// Fetch existing user
		UserModel existing = userRepository.findById(requestDto.getId())
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + requestDto.getId()));

		// Validate email uniqueness
		if (!existing.getEmail().equals(requestDto.getEmail()) && userRepository.existsByEmail(requestDto.getEmail())) {
			throw new DuplicateResourceException("Email already registered with another user", "email");
		}

		// Validate username uniqueness
		if (!existing.getUsername().equals(requestDto.getUsername())
			&& userRepository.existsByUsername(requestDto.getUsername())) {
			throw new DuplicateResourceException("Username already registered with another user", "username");
		}

		modelMapper.map(requestDto, existing);
		UserModel saved = userRepository.save(existing);
		return mapper.toDto(saved);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(Long id) {
		// Validate user existence
		UserModel user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		// Delete user
		userRepository.delete(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserResponseDto getUser(Long id) {
		UserModel user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		return mapper.toDto(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageResponseDto<UserResponseDto> getUsers(Long pageNo, Long pageSize, String sortBy, String sortDir) {
		// Build pagination configuration
		Pageable pageable = buildPageable(pageNo, pageSize, sortBy, sortDir);

		// Fetch paginated users
		Page<UserModel> page = userRepository.findAll(pageable);

		// Map entities to DTOs
		List<UserResponseDto> content = page.getContent()
			.stream()
			.map(mapper::toDto)
			.toList();

		// Build paginated response
		return new PageResponseDto<>(
			content,
			page.getNumber(),
			page.getSize(),
			page.getTotalElements(),
			page.getTotalPages(),
			page.hasNext(),
			page.hasPrevious());
	}

}