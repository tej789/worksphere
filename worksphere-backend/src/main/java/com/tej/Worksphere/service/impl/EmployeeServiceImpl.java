package com.tej.Worksphere.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tej.Worksphere.entity.Employee;
import com.tej.Worksphere.exception.DuplicateResourceException;
import com.tej.Worksphere.exception.ResourceNotFoundException;
import com.tej.Worksphere.repository.EmployeeRepository;
import com.tej.Worksphere.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	@Override
	public Employee createEmployee(Employee employee) {
		validateUniqueEmployee(employee, null);
		return employeeRepository.save(employee);
	}

	@Override
	public Employee updateEmployee(Long id, Employee employee) {
		Employee existingEmployee = getEmployeeById(id);
		validateUniqueEmployee(employee, id);
		existingEmployee.setEmployeeCode(employee.getEmployeeCode());
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setGender(employee.getGender());
		existingEmployee.setDateOfBirth(employee.getDateOfBirth());
		existingEmployee.setHireDate(employee.getHireDate());
		existingEmployee.setStatus(employee.getStatus());
		existingEmployee.setDepartment(employee.getDepartment());
		existingEmployee.setUser(employee.getUser());
		return employeeRepository.save(existingEmployee);
	}

	@Override
	@Transactional(readOnly = true)
	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Employee> getEmployeeByCode(String employeeCode) {
		return employeeRepository.findByEmployeeCode(employeeCode);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Employee> getEmployeeByUserId(Long userId) {
		return employeeRepository.findByUser_Id(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
		return employeeRepository.findByDepartment_Id(departmentId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> getEmployeesByStatus(String status) {
		return employeeRepository.findByStatus(status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public void deleteEmployee(Long id) {
		Employee existingEmployee = getEmployeeById(id);
		employeeRepository.delete(existingEmployee);
	}

	private void validateUniqueEmployee(Employee employee, Long currentEmployeeId) {
		employeeRepository.findByEmployeeCode(employee.getEmployeeCode())
				.filter(existing -> currentEmployeeId == null || !existing.getId().equals(currentEmployeeId))
				.ifPresent(existing -> {
					throw new DuplicateResourceException("Employee already exists with code: " + employee.getEmployeeCode());
				});

		if (employee.getUser() != null && employee.getUser().getId() != null) {
			employeeRepository.findByUser_Id(employee.getUser().getId())
					.filter(existing -> currentEmployeeId == null || !existing.getId().equals(currentEmployeeId))
					.ifPresent(existing -> {
						throw new DuplicateResourceException("Employee already exists for user id: " + employee.getUser().getId());
					});
		}
	}
}