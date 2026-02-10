package com.multitenant.app.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LoginResponseDto
 *
 * Represents authentication response returned after successful
 * tenant-based user login.
 *
 * This DTO contains JWT token and basic user session details
 * required by the frontend application.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDto {

	private String token;

	private String tokenType = "Bearer";

	private String companyCode;

	private String username;

	private String role;

}