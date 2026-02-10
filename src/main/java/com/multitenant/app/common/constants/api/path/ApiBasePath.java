package com.multitenant.app.common.constants.api.path;

/**
 * ApiBasePath
 *
 * Centralized base path definitions used across
 * all REST API endpoints.
 *
 * This class provides API versioning support and
 * common reusable URL fragments to maintain
 * consistency across controllers.
 */
public final class ApiBasePath {

	/* Base API version prefix */
	public static final String API = "/api";

	/* Common path variable for entity ID */
	public static final String ID = "/{id}";

	private ApiBasePath() {} // Prevent instantiation

}