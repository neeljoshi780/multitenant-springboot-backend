package com.multitenant.app.tenant.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserCreateRequestDto
 *
 * Request DTO used to create a new tenant user.
 * Contains validated user credentials and profile data
 * required for account creation.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequestDto {

	@NotBlank(message = "Email address is required.")
	@Email(message = "Email address must be a valid format.")
	@Size(max = 150, message = "Email address must not exceed 150 characters.")
	private String email;

	@NotBlank(message = "Username is required")
	@Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters.")
	private String username;

	@NotBlank(message = "Password is required.")
	@Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters.")
	private String password;

}