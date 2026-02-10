package com.multitenant.app.common.exception;

/**
 * TenantNotFoundException
 *
 * Thrown when tenant code does not exist
 * or tenant configuration is missing
 * in master database.
 *
 * This exception prevents invalid tenant
 * routing and protects tenant isolation.
 */
public class TenantNotFoundException extends RuntimeException {

	public TenantNotFoundException(String message) {
		super(message);
	}

}