package com.multitenant.app.master.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * TenantModel
 *
 * Entity representing tenant (company) information
 * stored in the master database.
 *
 * Acts as the control plane for multi-tenant onboarding,
 * identification, and tenant lifecycle management.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tenants")
@DynamicUpdate
public class TenantModel {

	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private UUID id;

	@Column(name = "company_code", nullable = false, unique = true, length = 50)
	private String companyCode;

	@Column(name = "company_name", nullable = false, length = 150)
	private String companyName;

	@Column(name = "company_email", nullable = false, unique = true, length = 150)
	private String companyEmail;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@OneToOne(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
	private TenantDbConfigModel tenantDbConfig;

}