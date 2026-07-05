package com.tej.Worksphere.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.tej.Worksphere.dto.LeaveRequestDTO;
import com.tej.Worksphere.dto.LeaveResponseDTO;
import com.tej.Worksphere.entity.Employee;
import com.tej.Worksphere.entity.Leave;
import com.tej.Worksphere.entity.User;
import com.tej.Worksphere.service.EmployeeService;
import com.tej.Worksphere.service.LeaveService;
import com.tej.Worksphere.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;
    private final EmployeeService employeeService;
    private final UserService userService;


    @PostMapping
@PreAuthorize("isAuthenticated()")
public ResponseEntity<LeaveResponseDTO> createLeave(
        @Valid @RequestBody LeaveRequestDTO requestDTO) {

    Leave leave = mapToEntity(requestDTO);

    Leave savedLeave = leaveService.createLeave(leave);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapToResponseDTO(savedLeave));
}

@GetMapping
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<List<LeaveResponseDTO>> getAllLeaves() {

    List<LeaveResponseDTO> leaves = leaveService.getAllLeaves()
            .stream()
            .map(this::mapToResponseDTO)
            .toList();

    return ResponseEntity.ok(leaves);
}

@GetMapping("/{id}")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<LeaveResponseDTO> getLeaveById(
        @PathVariable Long id) {

    Leave leave = leaveService.getLeaveById(id);

    return ResponseEntity.ok(mapToResponseDTO(leave));
}
@GetMapping("/employee/{employeeId}")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<List<LeaveResponseDTO>> getLeavesByEmployee(
        @PathVariable Long employeeId) {

    List<LeaveResponseDTO> leaves = leaveService.getLeavesByEmployee(employeeId)
            .stream()
            .map(this::mapToResponseDTO)
            .toList();

    return ResponseEntity.ok(leaves);
}

@PutMapping("/{id}")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<LeaveResponseDTO> updateLeave(
        @PathVariable Long id,
        @Valid @RequestBody LeaveRequestDTO requestDTO) {

    Leave leave = mapToEntity(requestDTO);

    Leave updatedLeave = leaveService.updateLeave(id, leave);

    return ResponseEntity.ok(mapToResponseDTO(updatedLeave));
}

@PutMapping("/{leaveId}/approve/{userId}")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<LeaveResponseDTO> approveLeave(
        @PathVariable Long leaveId,
        @PathVariable Long userId) {

    Leave leave = leaveService.approveLeave(leaveId, userId);

    return ResponseEntity.ok(mapToResponseDTO(leave));
}

@PutMapping("/{leaveId}/reject/{userId}")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<LeaveResponseDTO> rejectLeave(
        @PathVariable Long leaveId,
        @PathVariable Long userId) {

    Leave leave = leaveService.rejectLeave(leaveId, userId);

    return ResponseEntity.ok(mapToResponseDTO(leave));
}

@DeleteMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {

    leaveService.deleteLeave(id);

    return ResponseEntity.noContent().build();
}

private Leave mapToEntity(LeaveRequestDTO requestDTO) {

    Employee employee =
            employeeService.getEmployeeById(requestDTO.getEmployeeId());

    Leave leave = new Leave();

    leave.setEmployee(employee);
    leave.setLeaveType(requestDTO.getLeaveType());
    leave.setStartDate(requestDTO.getStartDate());
    leave.setEndDate(requestDTO.getEndDate());
    leave.setReason(requestDTO.getReason());

    return leave;
}

private LeaveResponseDTO mapToResponseDTO(Leave leave) {

    Employee employee = leave.getEmployee();
    User approvedBy = leave.getApprovedBy();

    return LeaveResponseDTO.builder()
            .id(leave.getId())
            .employeeId(employee != null ? employee.getId() : null)
            .employeeName(employee != null
                    ? employee.getFirstName() + " " + employee.getLastName()
                    : null)
            .leaveType(leave.getLeaveType())
            .startDate(leave.getStartDate())
            .endDate(leave.getEndDate())
            .reason(leave.getReason())
            .status(leave.getStatus())
            .approvedById(approvedBy != null ? approvedBy.getId() : null)
            .approvedByName(approvedBy != null
                    ? approvedBy.getFirstName() + " " + approvedBy.getLastName()
                    : null)
            .createdAt(leave.getCreatedAt())
            .updatedAt(leave.getUpdatedAt())
            .build();
}
}