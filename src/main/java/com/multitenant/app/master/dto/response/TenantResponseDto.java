package com.multitenant.app.master.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * TenantResponseDto
 *
 * Response DTO representing tenant master data
 * returned after successful tenant operations.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TenantResponseDto {

	private UUID id;

	private String companyCode;

	private String companyName;

	private String companyEmail;

	private String companyStatus;

}