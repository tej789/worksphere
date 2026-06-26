package com.tej.Worksphere.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDTO {

	@NotBlank(message = "Department code is required")
	@Size(max = 50, message = "Department code must not exceed 50 characters")
	private String departmentCode;

	@NotBlank(message = "Department name is required")
	@Size(max = 150, message = "Department name must not exceed 150 characters")
	private String name;

	@Size(max = 500, message = "Description must not exceed 500 characters")
	private String description;
}