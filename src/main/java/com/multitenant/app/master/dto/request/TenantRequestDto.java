package com.multitenant.app.master.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * TenantSignupRequestDto
 *
 * Request DTO used during tenant onboarding process.
 * Contains basic company registration information.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TenantRequestDto {

	@NotBlank
	private String companyCode;

	@NotBlank
	private String companyName;

	@NotBlank
	private String companyEmail;

}