package com.tej.Worksphere.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
@Table(name = "departments", uniqueConstraints = {
		@UniqueConstraint(name = "uk_departments_code", columnNames = "department_code"),
		@UniqueConstraint(name = "uk_departments_name", columnNames = "name")
})
public class Department extends AuditableEntity {

	@Column(name = "department_code", nullable = false, length = 50)
	private String departmentCode;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(length = 500)
	private String description;

	@Builder.Default
	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	private Set<Employee> employees = new HashSet<>();
}