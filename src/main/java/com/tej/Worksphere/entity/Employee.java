package com.tej.Worksphere.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees", uniqueConstraints = {
		@UniqueConstraint(name = "uk_employees_employee_code", columnNames = "employee_code"),
		@UniqueConstraint(name = "uk_employees_user_id", columnNames = "user_id")
})
public class Employee extends AuditableEntity {

	@Column(name = "employee_code", nullable = false, length = 50)
	private String employeeCode;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column(length = 20)
	private String gender;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "hire_date", nullable = false)
	private LocalDate hireDate;

	@Column(length = 20)
	private String status;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
}