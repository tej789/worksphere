package com.tej.Worksphere.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRoleRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long roleId;
}