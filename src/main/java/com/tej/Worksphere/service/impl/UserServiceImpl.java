package com.tej.Worksphere.service.impl;
import com.tej.Worksphere.entity.Role;
import com.tej.Worksphere.repository.RoleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tej.Worksphere.entity.User;
import com.tej.Worksphere.exception.DuplicateResourceException;
import com.tej.Worksphere.exception.ResourceNotFoundException;
import com.tej.Worksphere.repository.UserRepository;
import com.tej.Worksphere.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
private final RoleRepository roleRepository;
private final PasswordEncoder passwordEncoder;

	@Override
	public User createUser(User user) {
		validateUniqueUser(user, null);

// Encrypt password before saving
user.setPassword(passwordEncoder.encode(user.getPassword()));

return userRepository.save(user);
	}

	@Override
	public User updateUser(Long id, User user) {
		User existingUser = getUserById(id);
		validateUniqueUser(user, id);
		existingUser.setUsername(user.getUsername());
if (user.getPassword() != null && !user.getPassword().isBlank()) {
    existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
}		existingUser.setEmail(user.getEmail());
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setPhone(user.getPhone());
		existingUser.setRoles(user.getRoles());
		return userRepository.save(existingUser);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(Long id) {
		User existingUser = getUserById(id);
		userRepository.delete(existingUser);
	}
@Override
public User assignRole(Long userId, Long roleId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found with id: " + userId));

    Role role = roleRepository.findById(roleId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Role not found with id: " + roleId));

    // Prevent duplicate role assignment
    if (user.getRoles().stream().anyMatch(r -> r.getId().equals(roleId))) {
        throw new DuplicateResourceException(
                "Role '" + role.getName() + "' is already assigned to this user.");
    }

    user.getRoles().add(role);

    return userRepository.save(user);
}
	private void validateUniqueUser(User user, Long currentUserId) {
		userRepository.findByUsername(user.getUsername())
				.filter(existing -> currentUserId == null || !existing.getId().equals(currentUserId))
				.ifPresent(existing -> {
					throw new DuplicateResourceException("User already exists with username: " + user.getUsername());
				});

		userRepository.findByEmail(user.getEmail())
				.filter(existing -> currentUserId == null || !existing.getId().equals(currentUserId))
				.ifPresent(existing -> {
					throw new DuplicateResourceException("User already exists with email: " + user.getEmail());
				});
	}
}