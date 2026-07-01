package com.tej.Worksphere.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tej.Worksphere.entity.Attendance;
import com.tej.Worksphere.entity.Employee;
import com.tej.Worksphere.exception.DuplicateResourceException;
import com.tej.Worksphere.exception.ResourceNotFoundException;
import com.tej.Worksphere.repository.AttendanceRepository;
import com.tej.Worksphere.repository.EmployeeRepository;
import com.tej.Worksphere.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

	private final EmployeeRepository employeeRepository;
	private final AttendanceRepository attendanceRepository;

	@Override
	public Attendance createAttendance(Attendance attendance) {
		Employee employee = resolveEmployee(attendance.getEmployee().getId());
		validateDuplicateAttendance(employee.getId(), attendance.getDate(), null);
		attendance.setEmployee(employee);
		return attendanceRepository.save(attendance);
	}

	@Override
	public Attendance updateAttendance(Long id, Attendance attendance) {
		Attendance existingAttendance = getAttendanceById(id);
		Employee employee = resolveEmployee(attendance.getEmployee().getId());
		validateDuplicateAttendance(employee.getId(), attendance.getDate(), id);
		existingAttendance.setEmployee(employee);
		existingAttendance.setDate(attendance.getDate());
		existingAttendance.setCheckIn(attendance.getCheckIn());
		existingAttendance.setCheckOut(attendance.getCheckOut());
		existingAttendance.setStatus(attendance.getStatus());
		return attendanceRepository.save(existingAttendance);
	}

	@Override
	@Transactional(readOnly = true)
	public Attendance getAttendanceById(Long id) {
		return attendanceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getAllAttendance() {
		return attendanceRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Attendance> getAttendanceByEmployee(Long employeeId) {
		return attendanceRepository.findByEmployeeId(employeeId);
	}

	@Override
	public void deleteAttendance(Long id) {
		Attendance existingAttendance = getAttendanceById(id);
		attendanceRepository.delete(existingAttendance);
	}

	private Employee resolveEmployee(Long employeeId) {
		return employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
	}

	private void validateDuplicateAttendance(Long employeeId, java.time.LocalDate date, Long currentAttendanceId) {
		attendanceRepository.findByEmployeeIdAndDate(employeeId, date)
				.filter(existing -> currentAttendanceId == null || !existing.getId().equals(currentAttendanceId))
				.ifPresent(existing -> {
					throw new DuplicateResourceException(
							"Attendance already exists for employee id: " + employeeId + " on date: " + date);
				});
	}
}
