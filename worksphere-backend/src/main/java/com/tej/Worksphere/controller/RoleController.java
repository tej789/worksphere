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

import com.tej.Worksphere.dto.RoleRequestDTO;
import com.tej.Worksphere.dto.RoleResponseDTO;
import com.tej.Worksphere.entity.Role;
import com.tej.Worksphere.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

	private final RoleService roleService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO requestDTO) {
		Role role = mapToEntity(requestDTO);
		Role savedRole = roleService.createRole(role);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(savedRole));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
		List<RoleResponseDTO> roles = roleService.getAllRoles().stream()
				.map(this::mapToResponseDTO)
				.toList();
		return ResponseEntity.ok(roles);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
		Role role = roleService.getRoleById(id);
		return ResponseEntity.ok(mapToResponseDTO(role));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Long id,
														@Valid @RequestBody RoleRequestDTO requestDTO) {
		Role role = mapToEntity(requestDTO);
		Role updatedRole = roleService.updateRole(id, role);
		return ResponseEntity.ok(mapToResponseDTO(updatedRole));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
		return ResponseEntity.noContent().build();
	}

	private Role mapToEntity(RoleRequestDTO requestDTO) {
		Role role = new Role();
		role.setName(requestDTO.getName());
		role.setDescription(requestDTO.getDescription());
		return role;
	}

	private RoleResponseDTO mapToResponseDTO(Role role) {
		return RoleResponseDTO.builder()
				.id(role.getId())
				.name(role.getName())
				.description(role.getDescription())
				.createdAt(role.getCreatedAt())
				.updatedAt(role.getUpdatedAt())
				.build();
	}
}
