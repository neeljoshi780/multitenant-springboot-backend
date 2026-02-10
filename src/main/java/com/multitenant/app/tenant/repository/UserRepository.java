package com.multitenant.app.tenant.repository;

import com.multitenant.app.tenant.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository
 *
 * JPA repository for managing
 * tenant application users.
 *
 * Used for:
 * - Authentication lookup
 * - User CRUD operations
 * - Uniqueness validation
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

	/* Checks duplicate email */
	boolean existsByEmail(String email);

	/* Checks duplicate username */
	boolean existsByUsername(String username);

	/* Fetches user during login */
	Optional<UserModel> findByUsername(String username);

}