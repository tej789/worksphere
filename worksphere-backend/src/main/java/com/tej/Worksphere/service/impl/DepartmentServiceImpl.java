package com.tej.Worksphere.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tej.Worksphere.entity.Department;
import com.tej.Worksphere.exception.DuplicateResourceException;
import com.tej.Worksphere.exception.ResourceNotFoundException;
import com.tej.Worksphere.repository.DepartmentRepository;
import com.tej.Worksphere.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;

	@Override
	public Department createDepartment(Department department) {
		validateUniqueDepartment(department, null);
		return departmentRepository.save(department);
	}

	@Override
	public Department updateDepartment(Long id, Department department) {
		Department existingDepartment = getDepartmentById(id);
		validateUniqueDepartment(department, id);
		existingDepartment.setDepartmentCode(department.getDepartmentCode());
		existingDepartment.setName(department.getName());
		existingDepartment.setDescription(department.getDescription());
		return departmentRepository.save(existingDepartment);
	}

	@Override
	@Transactional(readOnly = true)
	public Department getDepartmentById(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Department> getDepartmentByCode(String departmentCode) {
		return departmentRepository.findByDepartmentCode(departmentCode);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Department> getDepartmentByName(String name) {
		return departmentRepository.findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	@Override
	public void deleteDepartment(Long id) {
		Department existingDepartment = getDepartmentById(id);
		departmentRepository.delete(existingDepartment);
	}

	private void validateUniqueDepartment(Department department, Long currentDepartmentId) {
		departmentRepository.findByDepartmentCode(department.getDepartmentCode())
				.filter(existing -> currentDepartmentId == null || !existing.getId().equals(currentDepartmentId))
				.ifPresent(existing -> {
					throw new DuplicateResourceException("Department already exists with code: " + department.getDepartmentCode());
				});

		departmentRepository.findByName(department.getName())
				.filter(existing -> currentDepartmentId == null || !existing.getId().equals(currentDepartmentId))
				.ifPresent(existing -> {
					throw new DuplicateResourceException("Department already exists with name: " + department.getName());
				});
	}
}