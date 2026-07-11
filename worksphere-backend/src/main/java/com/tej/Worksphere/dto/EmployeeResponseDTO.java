package com.tej.Worksphere.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {

	private Long id;
	private String employeeCode;
	private String firstName;
	private String lastName;
	private String gender;
	private LocalDate dateOfBirth;
	private LocalDate hireDate;
	private String status;
	private Long departmentId;
	private String departmentCode;
	private String departmentName;
	private Long userId;
	private String username;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String profileImage;
}