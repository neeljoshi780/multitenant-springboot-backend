package com.multitenant.app.common.exception;

import lombok.Getter;

/**
 * DuplicateResourceException
 *
 * Thrown when a resource violates uniqueness constraints
 * such as duplicate email, username, or tenant code.
 */
@Getter
public class DuplicateResourceException extends RuntimeException {

	private final String fieldName;

	public DuplicateResourceException(String message) {
		super(message);
		this.fieldName = null;
	}

	public DuplicateResourceException(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
	}

}