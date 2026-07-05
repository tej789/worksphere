package com.tej.Worksphere.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tej.Worksphere.entity.LeaveStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponseDTO {

	private Long id;
	private Long employeeId;
	private String employeeName;
	private String leaveType;
	private LocalDate startDate;
	private LocalDate endDate;
	private String reason;
	private LeaveStatus status;
	private Long approvedById;
	private String approvedByName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
