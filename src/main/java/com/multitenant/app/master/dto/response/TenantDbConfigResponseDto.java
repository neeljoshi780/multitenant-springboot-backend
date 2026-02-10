package com.multitenant.app.master.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * TenantDbConfigResponseDto
 *
 * Response DTO representing tenant database configuration
 * metadata used for internal monitoring and administration.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TenantDbConfigResponseDto {

	private Long id;

	private UUID tenantId;

	private String dbName;

	private String dbHost;

	private Integer dbPort;

	private String dbUsername;

	private String dbPassword;

	private LocalDateTime createdAt;

}