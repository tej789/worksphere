package com.tej.Worksphere.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDTO {

	private Long id;
	private String name;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
