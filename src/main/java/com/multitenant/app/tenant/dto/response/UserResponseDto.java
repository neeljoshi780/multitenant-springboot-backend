package com.multitenant.app.tenant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserResponseDto
 *
 * Response DTO returned after
 * tenant user operations.
 *
 * Used for listing and viewing
 * application users inside
 * tenant database.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {

	private Long id;

	private String email;

	private String username;

	private String role;

	private String status;

}