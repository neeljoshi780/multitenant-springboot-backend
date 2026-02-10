package com.multitenant.app.common.exception;

/**
 * ResourceNotFoundException
 *
 * Thrown when requested resource does not exist.
 *
 * Example:
 * - User not found
 * - Customer not found
 */
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String message) {
		super(message);
	}

}