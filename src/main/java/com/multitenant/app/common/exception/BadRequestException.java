package com.multitenant.app.common.exception;

/**
 * BadRequestException
 *
 * Thrown when client sends an invalid or malformed request
 * that violates business or validation rules.
 */
public class BadRequestException extends RuntimeException {

	public BadRequestException(String message) {
		super(message);
	}

}