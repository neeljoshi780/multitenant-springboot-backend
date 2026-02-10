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

/**
 * TenantDbConfigModel
 *
 * Entity representing tenant database configuration
 * stored in the master database.
 *
 * Used by dynamic routing datasource to resolve
 * tenant-specific database connections at runtime.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tenant_db_config")
@DynamicUpdate
public class TenantDbConfigModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tenant_id", nullable = false, unique = true)
	private TenantModel tenant;

	@Column(name = "db_name", nullable = false, unique = true)
	private String dbName;

	@Column(name = "db_host", nullable = false)
	private String dbHost;

	@Column(name = "db_port", nullable = false)
	private Integer dbPort;

	@Column(name = "db_username")
	private String dbUsername;

	@Column(name = "db_password")
	private String dbPassword;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}