package com.tej.Worksphere.service;

import java.util.List;
import java.util.Optional;

import com.tej.Worksphere.entity.Employee;

public interface EmployeeService {

	Employee createEmployee(Employee employee);

	Employee updateEmployee(Long id, Employee employee);

	Employee getEmployeeById(Long id);

	Optional<Employee> getEmployeeByCode(String employeeCode);

	Optional<Employee> getEmployeeByUserId(Long userId);

	List<Employee> getEmployeesByDepartmentId(Long departmentId);

	List<Employee> getEmployeesByStatus(String status);

	List<Employee> getAllEmployees();

	void deleteEmployee(Long id);
}