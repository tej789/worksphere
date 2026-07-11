package com.tej.Worksphere.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tej.Worksphere.entity.Role;
import com.tej.Worksphere.exception.DuplicateResourceException;
import com.tej.Worksphere.exception.ResourceNotFoundException;
import com.tej.Worksphere.repository.RoleRepository;
import com.tej.Worksphere.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Override
	public Role createRole(Role role) {
		if (roleRepository.existsByName(role.getName())) {
			throw new DuplicateResourceException("Role already exists with name: " + role.getName());
		}
		return roleRepository.save(role);
	}

	@Override
	public Role updateRole(Long id, Role role) {
		Role existingRole = getRoleById(id);
		if (!existingRole.getName().equals(role.getName()) && roleRepository.existsByName(role.getName())) {
			throw new DuplicateResourceException("Role already exists with name: " + role.getName());
		}
		existingRole.setName(role.getName());
		existingRole.setDescription(role.getDescription());
		return roleRepository.save(existingRole);
	}

	@Override
	@Transactional(readOnly = true)
	public Role getRoleById(Long id) {
		return roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public java.util.Optional<Role> getRoleByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public void deleteRole(Long id) {
		Role existingRole = getRoleById(id);
		roleRepository.delete(existingRole);
	}
}