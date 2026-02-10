package com.multitenant.app.tenant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * CustomerModel
 *
 * JPA entity representing
 * tenant customer records.
 *
 * Maps customer data to the
 * "customers" table and is
 * managed by Hibernate.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
@DynamicUpdate
public class CustomerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "age", columnDefinition = "TINYINT UNSIGNED")
	private Byte age;

	@Column(name = "gender", columnDefinition = "TINYINT UNSIGNED")
	private Byte gender;

	@Column(name = "mobile", nullable = false, unique = true, length = 20)
	private String mobile;

	@Column(name = "email", nullable = false, unique = true, length = 150)
	private String email;

	@Column(name = "address1", nullable = false, length = 255)
	private String address1;

	@Column(name = "address2", length = 255)
	private String address2;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}