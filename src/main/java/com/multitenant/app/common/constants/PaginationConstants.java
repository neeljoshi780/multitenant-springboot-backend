package com.multitenant.app.common.constants;

/**
 * PaginationConstants
 *
 * Centralized pagination configuration values used
 * across all pageable REST APIs.
 *
 * Helps enforce safe limits and consistent paging behavior.
 */
public final class PaginationConstants {

	/* Default page number */
	public static final int DEFAULT_PAGE_NO = 0;

	/* Default page size */
	public static final int DEFAULT_PAGE_SIZE = 10;

	/* Maximum allowed page size */
	public static final int MAX_PAGE_SIZE = 100;

	private PaginationConstants() {} // Prevent instantiation

}