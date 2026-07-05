package com.tej.Worksphere.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDTO {

	@NotNull(message = "Employee id is required")
	private Long employeeId;

	@NotBlank(message = "Leave type is required")
	private String leaveType;

	@NotNull(message = "Start date is required")
	private LocalDate startDate;

	@NotNull(message = "End date is required")
	private LocalDate endDate;

	@NotBlank(message = "Reason is required")
	private String reason;

}
