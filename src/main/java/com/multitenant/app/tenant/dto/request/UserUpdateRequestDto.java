package com.multitenant.app.tenant.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserUpdateRequestDto
 *
 * Request DTO used to update existing tenant user details.
 * Allows modification of profile information excluding password.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequestDto {

	@NotNull(message = "User ID is required.")
	private Long id;

	@NotBlank(message = "Email address is required.")
	@Email(message = "Email address must be a valid format.")
	@Size(max = 150, message = "Email address must not exceed 150 characters.")
	private String email;

	@NotBlank(message = "Username is required")
	@Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters.")
	private String username;

}