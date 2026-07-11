package com.tej.Worksphere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {

    private long totalEmployees;

    private long totalDepartments;

    private long totalAttendance;

    private long totalLeaves;

    private long pendingLeaves;

    private long totalPayrolls;

    private long totalPerformanceReviews;

}