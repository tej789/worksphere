package com.tej.Worksphere.service;

import java.util.List;
import java.util.Optional;

import com.tej.Worksphere.entity.Department;

public interface DepartmentService {

	Department createDepartment(Department department);

	Department updateDepartment(Long id, Department department);

	Department getDepartmentById(Long id);

	Optional<Department> getDepartmentByCode(String departmentCode);

	Optional<Department> getDepartmentByName(String name);

	List<Department> getAllDepartments();

	void deleteDepartment(Long id);
}