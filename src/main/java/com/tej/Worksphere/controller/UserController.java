package com.tej.Worksphere.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tej.Worksphere.dto.AssignRoleRequest;
import com.tej.Worksphere.dto.UserRequestDTO;
import com.tej.Worksphere.dto.UserResponseDTO;
import com.tej.Worksphere.entity.User;
import com.tej.Worksphere.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
		User user = mapToEntity(requestDTO);
		User savedUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(savedUser));
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		List<UserResponseDTO> users = userService.getAllUsers().stream()
				.map(this::mapToResponseDTO)
				.toList();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.ok(mapToResponseDTO(user));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
														@Valid @RequestBody UserRequestDTO requestDTO) {
		User user = mapToEntity(requestDTO);
		User updatedUser = userService.updateUser(id, user);
		return ResponseEntity.ok(mapToResponseDTO(updatedUser));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	@PostMapping("/assign-role")
public ResponseEntity<UserResponseDTO> assignRole(
        @Valid @RequestBody AssignRoleRequest request) {

    User updatedUser = userService.assignRole(
            request.getUserId(),
            request.getRoleId());

    return ResponseEntity.ok(mapToResponseDTO(updatedUser));
}

	private User mapToEntity(UserRequestDTO requestDTO) {
		User user = new User();
		user.setUsername(requestDTO.getUsername());
		user.setPassword(requestDTO.getPassword());
		user.setEmail(requestDTO.getEmail());
		user.setFirstName(requestDTO.getFirstName());
		user.setLastName(requestDTO.getLastName());
		user.setPhone(requestDTO.getPhone());
		return user;
	}

	private UserResponseDTO mapToResponseDTO(User user) {
		return UserResponseDTO.builder()
				.id(user.getId())
				.username(user.getUsername())
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.phone(user.getPhone())
				.roles(user.getRoles().stream()
						.map(role -> role.getName())
						.collect(Collectors.toSet()))
				.createdAt(user.getCreatedAt())
				.updatedAt(user.getUpdatedAt())
				.build();
	}
}
