package com.multitenant.app.master.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * TenantRegisterRequestDto
 *
 * Request DTO used during tenant registration process.
 * Contains company details and initial admin user credentials.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantRegisterRequestDto {

	@NotBlank(message = "Company code is required.")
	@Size(min = 2, max = 50, message = "Company code must be between 2 and 50 characters.")
	private String companyCode;

	@NotBlank(message = "Company name is required.")
	@Size(min = 2, max = 150, message = "Company name must be between 2 and 150 characters.")
	private String companyName;

	@NotBlank(message = "Company email address is required.")
	@Email(message = "Company email address must be a valid format.")
	@Size(max = 150, message = "Company email address must not exceed 150 characters.")
	private String companyEmail;

	@NotBlank(message = "Admin email address is required.")
	@Email(message = "Admin email address must be a valid format.")
	@Size(max = 150, message = "Admin email address must not exceed 150 characters.")
	private String adminEmail;

	@NotBlank(message = "Admin username is required.")
	@Size(min = 2, max = 50, message = "Admin username must be between 2 and 50 characters.")
	private String adminUsername;

	@NotBlank(message = "Admin password is required.")
	@Size(min = 8, max = 50, message = "Admin password must be between 8 and 50 characters.")
	private String adminPassword;

}