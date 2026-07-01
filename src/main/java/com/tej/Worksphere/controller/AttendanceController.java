package com.tej.Worksphere.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tej.Worksphere.dto.AttendanceRequestDTO;
import com.tej.Worksphere.dto.AttendanceResponseDTO;
import com.tej.Worksphere.entity.Attendance;
import com.tej.Worksphere.entity.Employee;
import com.tej.Worksphere.service.AttendanceService;
import com.tej.Worksphere.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

	private final AttendanceService attendanceService;
	private final EmployeeService employeeService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
	public ResponseEntity<AttendanceResponseDTO> createAttendance(@Valid @RequestBody AttendanceRequestDTO requestDTO) {
		Attendance attendance = mapToEntity(requestDTO);
		Attendance savedAttendance = attendanceService.createAttendance(attendance);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(savedAttendance));
	}

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<AttendanceResponseDTO>> getAllAttendance() {
		List<AttendanceResponseDTO> attendanceList = attendanceService.getAllAttendance().stream()
				.map(this::mapToResponseDTO)
				.toList();
		return ResponseEntity.ok(attendanceList);
	}

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<AttendanceResponseDTO> getAttendanceById(@PathVariable Long id) {
		Attendance attendance = attendanceService.getAttendanceById(id);
		return ResponseEntity.ok(mapToResponseDTO(attendance));
	}

	@GetMapping("/employee/{employeeId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceByEmployee(@PathVariable Long employeeId) {
		List<AttendanceResponseDTO> attendanceList = attendanceService.getAttendanceByEmployee(employeeId).stream()
				.map(this::mapToResponseDTO)
				.toList();
		return ResponseEntity.ok(attendanceList);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
	public ResponseEntity<AttendanceResponseDTO> updateAttendance(@PathVariable Long id,
																									 @Valid @RequestBody AttendanceRequestDTO requestDTO) {
		Attendance attendance = mapToEntity(requestDTO);
		Attendance updatedAttendance = attendanceService.updateAttendance(id, attendance);
		return ResponseEntity.ok(mapToResponseDTO(updatedAttendance));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
	public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
		attendanceService.deleteAttendance(id);
		return ResponseEntity.noContent().build();
	}

	private Attendance mapToEntity(AttendanceRequestDTO requestDTO) {
		Employee employee = employeeService.getEmployeeById(requestDTO.getEmployeeId());

		Attendance attendance = new Attendance();
		attendance.setEmployee(employee);
		attendance.setDate(requestDTO.getDate());
		attendance.setCheckIn(requestDTO.getCheckIn());
		attendance.setCheckOut(requestDTO.getCheckOut());
		attendance.setStatus(requestDTO.getStatus());
		return attendance;
	}

	private AttendanceResponseDTO mapToResponseDTO(Attendance attendance) {
		Employee employee = attendance.getEmployee();

		return AttendanceResponseDTO.builder()
				.id(attendance.getId())
				.employeeId(employee != null ? employee.getId() : null)
				.employeeName(employee != null ? employee.getFirstName() + " " + employee.getLastName() : null)
				.date(attendance.getDate())
				.checkIn(attendance.getCheckIn())
				.checkOut(attendance.getCheckOut())
				.status(attendance.getStatus())
				.createdAt(attendance.getCreatedAt())
				.updatedAt(attendance.getUpdatedAt())
				.build();
	}
}
