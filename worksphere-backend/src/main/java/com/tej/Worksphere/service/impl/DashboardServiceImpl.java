package com.tej.Worksphere.service.impl;

import org.springframework.stereotype.Service;
import com.tej.Worksphere.entity.LeaveStatus;
import com.tej.Worksphere.dto.DashboardResponseDTO;
import com.tej.Worksphere.entity.LeaveStatus;
import com.tej.Worksphere.repository.AttendanceRepository;
import com.tej.Worksphere.repository.DepartmentRepository;
import com.tej.Worksphere.repository.EmployeeRepository;
import com.tej.Worksphere.repository.LeaveRepository;
import com.tej.Worksphere.repository.PayrollRepository;
import com.tej.Worksphere.repository.PerformanceReviewRepository;
import com.tej.Worksphere.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRepository leaveRepository;
    private final PayrollRepository payrollRepository;
    private final PerformanceReviewRepository performanceReviewRepository;


    @Override
public DashboardResponseDTO getDashboard() {

    return DashboardResponseDTO.builder()
            .totalEmployees(employeeRepository.count())
            .totalDepartments(departmentRepository.count())
            .totalAttendance(attendanceRepository.count())
            .totalLeaves(leaveRepository.count())
            .pendingLeaves(
                    leaveRepository.findByStatus(LeaveStatus.PENDING).size())
            .totalPayrolls(payrollRepository.count())
            .totalPerformanceReviews(performanceReviewRepository.count())
            .build();
}
}