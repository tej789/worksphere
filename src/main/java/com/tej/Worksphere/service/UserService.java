package com.tej.Worksphere.service;

import java.util.List;
import java.util.Optional;

import com.tej.Worksphere.entity.User;

public interface UserService {

	User createUser(User user);

	User updateUser(Long id, User user);

	User getUserById(Long id);

	Optional<User> getUserByUsername(String username);

	Optional<User> getUserByEmail(String email);

	List<User> getAllUsers();

	void deleteUser(Long id);

	User assignRole(Long userId, Long roleId);
}