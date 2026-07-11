package com.tej.Worksphere.service;

import java.util.List;

import com.tej.Worksphere.entity.Attendance;

public interface AttendanceService {

	Attendance createAttendance(Attendance attendance);

	Attendance updateAttendance(Long id, Attendance attendance);

	Attendance getAttendanceById(Long id);

	List<Attendance> getAllAttendance();

	List<Attendance> getAttendanceByEmployee(Long employeeId);

	void deleteAttendance(Long id);
}
