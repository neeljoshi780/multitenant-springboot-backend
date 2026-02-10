package com.multitenant.app.tenant.specification;

import com.multitenant.app.tenant.model.CustomerModel;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Expression;

/**
 * CustomerSpecification
 *
 * Provides dynamic search and filtering logic
 * for Customer entity using JPA Specifications.
 *
 * Supports global search across multiple
 * customer fields.
 */
public class CustomerSpecification {

	/**
	 * Creates global search specification.
	 *
	 * Applies OR-based filtering on:
	 * - Name fields
	 * - Contact details
	 * - Address fields
	 * - ID, DOB, Gender (if parsable)
	 *
	 * @param search search keyword
	 * @return specification predicate
	 */
	public static Specification<CustomerModel> globalSearch(String search) {

		return (root, query, cb) -> {
			// Return no filter when search is empty
			if (search == null || search.isBlank())
				return cb.conjunction(); // No filtering applied

			String likePattern = "%" + search.toLowerCase() + "%";

			// Handle Gender mapping for search (0: MALE, 1: FEMALE, 2: OTHER)
			Expression<String> genderString = cb.selectCase()
				.when(cb.equal(root.get("gender"), (byte) 0), "MALE")
				.when(cb.equal(root.get("gender"), (byte) 1), "FEMALE")
				.otherwise("OTHER").as(String.class);

			return cb.or(
				cb.like(cb.lower(root.get("firstName")), likePattern),
				cb.like(cb.lower(root.get("lastName")), likePattern),
				cb.like(cb.lower(root.get("email")), likePattern),
				cb.like(cb.lower(root.get("mobile")), likePattern),
				cb.like(cb.lower(root.get("address1")), likePattern),
				cb.like(cb.lower(root.get("address2")), likePattern),
				cb.like(root.get("age").as(String.class), likePattern),
				cb.like(root.get("dateOfBirth").as(String.class), likePattern),
				cb.like(cb.lower(genderString), likePattern));
		};
	}

}