package com.multitenant.app.tenant.repository;

import com.multitenant.app.tenant.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * CustomerRepository
 *
 * JPA repository for managing
 * customer records inside
 * tenant databases.
 *
 * Provides CRUD operations,
 * search support and
 * uniqueness validation.
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long>, JpaSpecificationExecutor<CustomerModel> {

	/* Checks duplicate mobile number */
	boolean existsByMobile(String mobile);

	/* Checks duplicate email address */
	boolean existsByEmail(String email);

}