package com.tej.Worksphere.controller;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import com.tej.Worksphere.dto.EmployeeRequestDTO;
import com.tej.Worksphere.dto.EmployeeResponseDTO;
import com.tej.Worksphere.entity.Department;
import com.tej.Worksphere.entity.Employee;
import com.tej.Worksphere.entity.User;
import com.tej.Worksphere.service.DepartmentService;
import com.tej.Worksphere.service.EmployeeService;
import com.tej.Worksphere.service.FileStorageService;
import com.tej.Worksphere.service.UserService;
import com.tej.Worksphere.service.ExcelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService employeeService;
	private final DepartmentService departmentService;
	private final UserService userService;
private final FileStorageService fileStorageService;
private final ExcelService excelService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO) {
		Employee employee = mapToEntity(requestDTO);
		Employee savedEmployee = employeeService.createEmployee(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(savedEmployee));
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
		List<EmployeeResponseDTO> employees = employeeService.getAllEmployees().stream()
				.map(this::mapToResponseDTO)
				.toList();
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(mapToResponseDTO(employee));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id,
																 @Valid @RequestBody EmployeeRequestDTO requestDTO) {
		Employee employee = mapToEntity(requestDTO);
		Employee updatedEmployee = employeeService.updateEmployee(id, employee);
		return ResponseEntity.ok(mapToResponseDTO(updatedEmployee));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/export")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<byte[]> exportEmployees() {

    byte[] excel = excelService.exportEmployees();

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=employees.xlsx")
.contentType(
    MediaType.parseMediaType(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))            .body(excel);
}

	private Employee mapToEntity(EmployeeRequestDTO requestDTO) {
		Department department = departmentService.getDepartmentById(requestDTO.getDepartmentId());
		User user = userService.getUserById(requestDTO.getUserId());

		Employee employee = new Employee();
		employee.setEmployeeCode(requestDTO.getEmployeeCode());
		employee.setFirstName(requestDTO.getFirstName());
		employee.setLastName(requestDTO.getLastName());
		employee.setGender(requestDTO.getGender());
		employee.setDateOfBirth(requestDTO.getDateOfBirth());
		employee.setHireDate(requestDTO.getHireDate());
		employee.setStatus(requestDTO.getStatus());
		employee.setDepartment(department);
		employee.setUser(user);
		return employee;
	}

	private EmployeeResponseDTO mapToResponseDTO(Employee employee) {
		Department department = employee.getDepartment();
		User user = employee.getUser();

		return EmployeeResponseDTO.builder()
				.id(employee.getId())
				.employeeCode(employee.getEmployeeCode())
				.firstName(employee.getFirstName())
				.lastName(employee.getLastName())
				.profileImage(employee.getProfileImage())
				.gender(employee.getGender())
				.dateOfBirth(employee.getDateOfBirth())
				.hireDate(employee.getHireDate())
				.status(employee.getStatus())
				.departmentId(department != null ? department.getId() : null)
				.departmentCode(department != null ? department.getDepartmentCode() : null)
				.departmentName(department != null ? department.getName() : null)
				.userId(user != null ? user.getId() : null)
				.username(user != null ? user.getUsername() : null)
				.createdAt(employee.getCreatedAt())
				.updatedAt(employee.getUpdatedAt())
				.build();
	}

	@PostMapping("/{id}/upload-photo")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<String> uploadProfilePhoto(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file) {

    String fileName = fileStorageService.uploadProfileImage(id, file);

    return ResponseEntity.ok("Profile photo uploaded successfully: " + fileName);
}

@GetMapping("/photo/{fileName}")
public ResponseEntity<Resource> getProfilePhoto(
        @PathVariable String fileName) {

    Resource resource =
            fileStorageService.loadProfileImage(fileName);

    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(resource);
}
}