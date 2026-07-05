package com.tej.Worksphere.service;

import java.util.List;

import com.tej.Worksphere.entity.Leave;

public interface LeaveService {

    Leave createLeave(Leave leave);

    Leave updateLeave(Long id, Leave leave);

    Leave getLeaveById(Long id);

    List<Leave> getAllLeaves();

    List<Leave> getLeavesByEmployee(Long employeeId);

    Leave approveLeave(Long leaveId, Long approvedByUserId);

    Leave rejectLeave(Long leaveId, Long approvedByUserId);

    void deleteLeave(Long id);
}