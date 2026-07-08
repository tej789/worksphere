package com.tej.Worksphere.service.impl;

import java.util.List;
import com.tej.Worksphere.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tej.Worksphere.entity.Leave;
import com.tej.Worksphere.entity.LeaveStatus;
import com.tej.Worksphere.entity.User;
import com.tej.Worksphere.exception.ResourceNotFoundException;
import com.tej.Worksphere.repository.EmployeeRepository;
import com.tej.Worksphere.repository.LeaveRepository;
import com.tej.Worksphere.repository.UserRepository;
import com.tej.Worksphere.service.LeaveService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

  @Override
public Leave createLeave(Leave leave) {

    employeeRepository.findById(leave.getEmployee().getId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Employee not found with id: "
                                    + leave.getEmployee().getId()));

    if (leave.getEndDate().isBefore(leave.getStartDate())) {
        throw new IllegalArgumentException(
                "End date cannot be before start date");
    }

    leave.setStatus(LeaveStatus.PENDING);

    return leaveRepository.save(leave);
}

@Override
public Leave updateLeave(Long id, Leave leave) {

    Leave existingLeave = getLeaveById(id);
if (existingLeave.getStatus() != LeaveStatus.PENDING) {
    throw new IllegalArgumentException(
            "Only pending leave requests can be updated.");
}
    employeeRepository.findById(leave.getEmployee().getId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Employee not found with id: "
                                    + leave.getEmployee().getId()));

    if (leave.getEndDate().isBefore(leave.getStartDate())) {
        throw new IllegalArgumentException(
                "End date cannot be before start date");
    }

    existingLeave.setEmployee(leave.getEmployee());
    existingLeave.setLeaveType(leave.getLeaveType());
    existingLeave.setStartDate(leave.getStartDate());
    existingLeave.setEndDate(leave.getEndDate());
    existingLeave.setReason(leave.getReason());

    return leaveRepository.save(existingLeave);
}

@Override
@Transactional(readOnly = true)
public Leave getLeaveById(Long id) {

    return leaveRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Leave not found with id: " + id));
}

@Override
@Transactional(readOnly = true)
public List<Leave> getAllLeaves() {

    return leaveRepository.findAll();
}

@Override
@Transactional(readOnly = true)
public List<Leave> getLeavesByEmployee(Long employeeId) {

    employeeRepository.findById(employeeId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Employee not found with id: " + employeeId));

    return leaveRepository.findByEmployeeId(employeeId);
}

@Override
public Leave approveLeave(Long leaveId, Long approvedByUserId) {

    Leave leave = getLeaveById(leaveId);

    User approvedBy = userRepository.findById(approvedByUserId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User not found with id: " + approvedByUserId));

   if (leave.getStatus() == LeaveStatus.APPROVED) {
    throw new IllegalArgumentException("Leave is already approved.");
}
   leave.setStatus(LeaveStatus.APPROVED);
leave.setApprovedBy(approvedBy);

String email = leave.getEmployee().getEmail();

if (email == null || email.isBlank()) {
    throw new RuntimeException("Employee email is missing.");
}

emailService.sendLeaveApprovedEmail(
        email,
        leave.getEmployee().getFirstName(),
        leave.getLeaveType());

return leaveRepository.save(leave);
}

@Override
public Leave rejectLeave(Long leaveId, Long approvedByUserId) {

    Leave leave = getLeaveById(leaveId);

    User approvedBy = userRepository.findById(approvedByUserId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User not found with id: " + approvedByUserId));

   if (leave.getStatus() == LeaveStatus.REJECTED) {
    throw new IllegalArgumentException("Leave is already rejected.");
}
    leave.setStatus(LeaveStatus.REJECTED);
    leave.setApprovedBy(approvedBy);
    
emailService.sendLeaveRejectedEmail(
        leave.getEmployee().getEmail(),
        leave.getEmployee().getFirstName(),
        leave.getLeaveType());

    return leaveRepository.save(leave);
}

@Override
public void deleteLeave(Long id) {

    Leave leave = getLeaveById(id);

    leaveRepository.delete(leave);
}
}