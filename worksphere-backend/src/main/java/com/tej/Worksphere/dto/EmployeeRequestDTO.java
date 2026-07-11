package com.tej.Worksphere.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {

	@NotBlank(message = "Employee code is required")
	@Size(max = 50, message = "Employee code must not exceed 50 characters")
	private String employeeCode;

	@NotBlank(message = "First name is required")
	@Size(max = 100, message = "First name must not exceed 100 characters")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(max = 100, message = "Last name must not exceed 100 characters")
	private String lastName;

	@Size(max = 20, message = "Gender must not exceed 20 characters")
	private String gender;

	@PastOrPresent(message = "Date of birth must be in the past or present")
	private LocalDate dateOfBirth;

	@NotNull(message = "Hire date is required")
	@PastOrPresent(message = "Hire date must be in the past or present")
	private LocalDate hireDate;

	@Size(max = 20, message = "Status must not exceed 20 characters")
	private String status;

	@NotNull(message = "Department id is required")
	private Long departmentId;

	@NotNull(message = "User id is required")
	private Long userId;
}