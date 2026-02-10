package com.multitenant.app.master.repository;

import com.multitenant.app.master.model.TenantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * TenantRepository
 *
 * Repository interface for managing tenant master
 * records stored in the master database.
 *
 * Provides lookup and validation operations required
 * for multi-tenant onboarding and routing.
 */
@Repository
public interface TenantRepository extends JpaRepository<TenantModel, UUID> {

	/**
	 * Checks if tenant already exists using company code.
	 *
	 * @param companyCode unique tenant company code
	 * @return true if tenant exists, otherwise false
	 */
	boolean existsByCompanyCode(String companyCode);

	/**
	 * Checks if tenant already exists using company email.
	 *
	 * @param companyEmail tenant registered email
	 * @return true if tenant exists, otherwise false
	 */
	boolean existsByCompanyEmail(String companyEmail);

	/**
	 * Retrieves tenant using company code.
	 *
	 * @param companyCode tenant identifier code
	 * @return optional tenant master record
	 */
	Optional<TenantModel> findByCompanyCode(String companyCode);

}