package com.multitenant.app.tenant.controller;

import com.multitenant.app.common.constants.api.path.ApiBasePath;
import com.multitenant.app.common.constants.api.path.CustomerApiPath;
import com.multitenant.app.common.response.PageResponseDto;
import com.multitenant.app.tenant.dto.request.CustomerRequestDto;
import com.multitenant.app.tenant.dto.response.CustomerResponseDto;
import com.multitenant.app.tenant.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CustomerController
 *
 * REST controller for tenant customer management APIs.
 *
 * Handles CRUD operations and paginated customer
 * listing within tenant database context.
 */
@RestController
@RequestMapping(CustomerApiPath.BASE)
@RequiredArgsConstructor
public class CustomerController {

	/* Handles customer business operations */
	private final CustomerService customerService;

	/**
	 * Creates a new customer record.
	 */
	@PostMapping
	public ResponseEntity<CustomerResponseDto> createCustomer(
			@Valid @RequestBody CustomerRequestDto customerRequestDto) {
		CustomerResponseDto createdCustomer = customerService.createCustomer(customerRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
	}

	/**
	 * Fetches customer details by ID.
	 */
	@GetMapping(ApiBasePath.ID)
	public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
		CustomerResponseDto customer = customerService.getCustomer(id);
		return ResponseEntity.ok(customer);
	}

	/**
	 * Returns paginated customer list with search and sorting.
	 */
	@GetMapping
	public ResponseEntity<PageResponseDto<CustomerResponseDto>> getCustomers(
		@RequestParam(required = false) String search,
		@RequestParam(defaultValue = "0") Long pageNo,
		@RequestParam(defaultValue = "10") Long pageSize,
		@RequestParam(defaultValue = "id") String sortBy,
		@RequestParam(defaultValue = "asc") String sortDir) {

		PageResponseDto<CustomerResponseDto> response = customerService.getCustomers(search, pageNo, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(response);
	}

	/**
	 * Updates existing customer data.
	 */
	@PutMapping
	public ResponseEntity<CustomerResponseDto> updateCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
		CustomerResponseDto updatedCustomer = customerService.updateCustomer(customerRequestDto);
		return ResponseEntity.ok(updatedCustomer);
	}

	/**
	 * Deletes customer by ID.
	 */
	@DeleteMapping(ApiBasePath.ID)
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}

}