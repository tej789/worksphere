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

import com.tej.Worksphere.dto.DepartmentRequestDTO;
import com.tej.Worksphere.dto.DepartmentResponseDTO;
import com.tej.Worksphere.entity.Department;
import com.tej.Worksphere.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public ResponseEntity<DepartmentResponseDTO> createDepartment(@Valid @RequestBody DepartmentRequestDTO requestDTO) {
		Department department = mapToEntity(requestDTO);
		Department savedDepartment = departmentService.createDepartment(department);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(savedDepartment));
	}

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
		List<DepartmentResponseDTO> departments = departmentService.getAllDepartments().stream()
				.map(this::mapToResponseDTO)
				.toList();
		return ResponseEntity.ok(departments);
	}

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long id) {
		Department department = departmentService.getDepartmentById(id);
		return ResponseEntity.ok(mapToResponseDTO(department));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable Long id,
																	 @Valid @RequestBody DepartmentRequestDTO requestDTO) {
		Department department = mapToEntity(requestDTO);
		Department updatedDepartment = departmentService.updateDepartment(id, department);
		return ResponseEntity.ok(mapToResponseDTO(updatedDepartment));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
		departmentService.deleteDepartment(id);
		return ResponseEntity.noContent().build();
	}

	private Department mapToEntity(DepartmentRequestDTO requestDTO) {
		Department department = new Department();
		department.setDepartmentCode(requestDTO.getDepartmentCode());
		department.setName(requestDTO.getName());
		department.setDescription(requestDTO.getDescription());
		return department;
	}

	private DepartmentResponseDTO mapToResponseDTO(Department department) {
		return DepartmentResponseDTO.builder()
				.id(department.getId())
				.departmentCode(department.getDepartmentCode())
				.name(department.getName())
				.description(department.getDescription())
				.createdAt(department.getCreatedAt())
				.updatedAt(department.getUpdatedAt())
				.build();
	}
}