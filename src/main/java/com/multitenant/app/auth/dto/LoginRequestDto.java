package com.multitenant.app.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LoginRequestDto
 *
 * Represents login request payload for multi-tenant authentication.
 *
 * This DTO carries tenant identifier and user credentials required
 * for validating user access and routing requests to correct tenant database.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {

	@NotBlank(message = "Company code is required")
	private String companyCode;

	@NotBlank(message = "Username is required")
	private String username;

	@NotBlank(message = "Password is required")
	private String password;

}