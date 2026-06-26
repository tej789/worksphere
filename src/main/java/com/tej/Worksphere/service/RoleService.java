package com.tej.Worksphere.service;

import java.util.List;
import java.util.Optional;

import com.tej.Worksphere.entity.Role;

public interface RoleService {

	Role createRole(Role role);

	Role updateRole(Long id, Role role);

	Role getRoleById(Long id);

	Optional<Role> getRoleByName(String name);

	List<Role> getAllRoles();

	void deleteRole(Long id);
}