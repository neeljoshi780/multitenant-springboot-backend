package com.multitenant.app.tenant.service.impl;

import com.multitenant.app.common.constants.PaginationConstants;
import com.multitenant.app.common.exception.DuplicateResourceException;
import com.multitenant.app.common.exception.ResourceNotFoundException;
import com.multitenant.app.common.mapper.CustomerMapper;
import com.multitenant.app.common.response.PageResponseDto;
import com.multitenant.app.tenant.dto.request.CustomerRequestDto;
import com.multitenant.app.tenant.dto.response.CustomerResponseDto;
import com.multitenant.app.tenant.model.CustomerModel;
import com.multitenant.app.tenant.repository.CustomerRepository;
import com.multitenant.app.tenant.service.CustomerService;
import com.multitenant.app.tenant.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomerServiceImpl
 *
 * Concrete implementation of CustomerService.
 *
 * Handles tenant customer business logic,
 * validation rules, and database persistence
 * operations using repository and mapper.
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	/* Repository for customer persistence operations */
	private final CustomerRepository customerRepository;

	// Object mapping
	private final ModelMapper modelMapper;

	/* Mapper for converting entity and DTO */
	private final CustomerMapper mapper;

	/**
	 * Builds pageable object with validation and sorting.
	 *
	 * @param pageNo   page number
	 * @param pageSize page size
	 * @param sortBy   sorting field
	 * @param sortDir  sorting direction
	 * @return pageable configuration
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
	public CustomerResponseDto createCustomer(CustomerRequestDto dto) {
		// Validate unique email
		if (customerRepository.existsByEmail(dto.getEmail())) {
			throw new DuplicateResourceException("Email already registered", "email");
		}

		// Validate unique mobile number
		if (customerRepository.existsByMobile(dto.getMobile())) {
			throw new DuplicateResourceException("Mobile number already registered", "mobile");
		}

		// Map request to entity and persist
		CustomerModel entity = mapper.toEntity(dto);
		CustomerModel saved = customerRepository.save(entity);
		return mapper.toDto(saved);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CustomerResponseDto updateCustomer(CustomerRequestDto requestDto) {
		// Fetch existing customer
		CustomerModel existing = customerRepository.findById(requestDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + requestDto.getId()));

		// Validate email uniqueness
		if (!existing.getEmail().equals(requestDto.getEmail())
				&& customerRepository.existsByEmail(requestDto.getEmail())) {
			throw new DuplicateResourceException("Email already registered with another customer", "email");
		}

		// Validate mobile uniqueness
		if (!existing.getMobile().equals(requestDto.getMobile())
				&& customerRepository.existsByMobile(requestDto.getMobile())) {
			throw new DuplicateResourceException("Mobile already registered with another customer", "mobile");
		}

		modelMapper.map(requestDto, existing);

		// Manually update gender as it is skipped in mapper
		if (requestDto.getGender() != null) {
			existing.setGender(com.multitenant.app.tenant.enums.Gender.fromString(requestDto.getGender()));
		}
		CustomerModel saved = customerRepository.save(existing);
		return mapper.toDto(saved);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteCustomer(Long id) {
		// Validate customer existence
		CustomerModel customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
		// Delete customer record
		customerRepository.delete(customer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CustomerResponseDto getCustomer(Long id) {
		// Fetch customer by ID
		CustomerModel customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
		return mapper.toDto(customer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageResponseDto<CustomerResponseDto> getCustomers(String search, Long pageNo, Long pageSize, String sortBy,
			String sortDir) {
		// Build pagination and sorting configuration
		Pageable pageable = buildPageable(pageNo, pageSize, sortBy, sortDir);

		// Apply global search filter
		Specification<CustomerModel> specification = CustomerSpecification.globalSearch(search);

		// Fetch paginated data
		Page<CustomerModel> page = customerRepository.findAll(specification, pageable);

		// Map entity list to response DTO list
		List<CustomerResponseDto> content = page.getContent()
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