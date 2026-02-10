package com.multitenant.app.tenant.service;

import com.multitenant.app.common.response.PageResponseDto;
import com.multitenant.app.tenant.dto.request.CustomerRequestDto;
import com.multitenant.app.tenant.dto.response.CustomerResponseDto;

/**
 * CustomerService
 *
 * Defines tenant customer business operations.
 *
 * Acts as the service layer contract between
 * controller and persistence layer.
 */
public interface CustomerService {

	/**
	 * Creates a new customer record.
	 *
	 * @param customerRequestDto customer input data
	 * @return created customer response object
	 */
	CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto);

	/**
	 * Updates an existing customer record.
	 *
	 * @param customerRequestDto updated customer data
	 * @return updated customer response object
	 */
	CustomerResponseDto updateCustomer(CustomerRequestDto customerRequestDto);

	/**
	 * Deletes customer using unique ID.
	 *
	 * @param id customer identifier
	 */
	void deleteCustomer(Long id);

	/**
	 * Retrieves customer details by ID.
	 *
	 * @param id customer identifier
	 * @return customer response data
	 */
	CustomerResponseDto getCustomer(Long id);

	/**
	 * Retrieves paginated customer list with optional search and sorting.
	 *
	 * @param search keyword filter (optional)
	 * @param pageNo page index (zero based)
	 * @param pageSize number of records per page
	 * @param sortBy sorting field name
	 * @param sortDir sorting direction (asc / desc)
	 * @return paginated customer response data
	 */
	PageResponseDto<CustomerResponseDto> getCustomers(String search, Long pageNo, Long pageSize, String sortBy, String sortDir);

}