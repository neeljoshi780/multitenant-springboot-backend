package com.multitenant.app.master.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * TenantDbConfigRequestDto
 *
 * Request DTO used to store tenant database connection
 * configuration after tenant database provisioning.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TenantDbConfigRequestDto {

	@NotBlank
	private String dbName;

	@NotBlank
	private String dbHost;

	@NotNull
	private Integer dbPort;

	@NotBlank
	private String dbUsername;

	@NotBlank
	private String dbPassword;

}