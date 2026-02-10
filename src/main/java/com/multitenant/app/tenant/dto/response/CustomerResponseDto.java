package com.multitenant.app.tenant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * CustomerResponseDto
 *
 * Response DTO used to return
 * customer details to the client.
 *
 * Represents a read-only view of
 * customer data fetched from
 * tenant database.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerResponseDto {

	private Long id;

	private String firstName;

	private String lastName;

	private LocalDate dateOfBirth;

	private Byte age;

	private String gender;

	private String mobile;

	private String email;

	private String address1;

	private String address2;

}