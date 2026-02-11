package com.multitenant.app.tenant.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * CustomerRequestDto
 *
 * Request DTO used for creating and updating
 * customer records inside tenant database.
 *
 * Applies validation rules to ensure
 * incoming request data is safe and consistent
 * before reaching service layer.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerRequestDto {

	private Long id;

	@NotBlank(message = "First name is required.")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "First name must contain only alphanumeric characters.")
	@Size(max = 50, message = "First name must not exceed 50 characters.")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Last name contains invalid characters")
	@Size(max = 50, message = "Last name must not exceed 50 characters")
	private String lastName;

	@NotNull(message = "Date of birth is required.")
	private LocalDate dateOfBirth;

	@NotNull(message = "Age is required.")
	@Min(value = 0, message = "Age must be greater than or equal to 0.")
	@Max(value = 120, message = "Age must be less than or equal to 120.")
	private Byte age;

	@NotBlank(message = "Gender is required.")
	private String gender;

	@NotBlank(message = "Mobile number is required.")
	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Mobile number must be a valid 10-digit Indian mobile number.")
	private String mobile;

	@NotBlank(message = "Email address is required.")
	@Email(message = "Email address must be a valid format.")
	@Size(max = 150, message = "Email address must not exceed 150 characters.")
	private String email;

	@NotBlank(message = "Address line 1 is required.")
	@Pattern(regexp = "^[A-Za-z0-9,.\\-/#@\\s\\r\\n]+$", message = "Address line 1 contains invalid characters.")
	@Size(max = 255, message = "Address line 1 must not exceed 255 characters.")
	private String address1;

	@Pattern(regexp = "^[A-Za-z0-9,.\\-/#@\\s\\r\\n]+$", message = "Address line 2 contains invalid characters.")
	@Size(max = 255, message = "Address line 2 must not exceed 255 characters.")
	private String address2;

}