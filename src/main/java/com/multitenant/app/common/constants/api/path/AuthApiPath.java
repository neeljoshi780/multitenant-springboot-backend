package com.multitenant.app.common.constants.api.path;

/**
 * AuthApiPath
 *
 * Defines REST endpoint paths related to
 * authentication and authorization operations.
 *
 * This includes login, token refresh, and logout APIs.
 */
public final class AuthApiPath {

	/* Base path for authentication APIs */
	public static final String BASE = ApiBasePath.API + "/auth";

	/* Login endpoint */
	public static final String LOGIN = "/login";

	private AuthApiPath() {} // Prevent instantiation

}