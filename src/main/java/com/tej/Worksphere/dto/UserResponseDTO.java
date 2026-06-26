package com.tej.Worksphere.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

	private Long id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String phone;
	private Set<String> roles;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
