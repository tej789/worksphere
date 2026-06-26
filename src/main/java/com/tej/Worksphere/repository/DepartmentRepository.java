package com.tej.Worksphere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tej.Worksphere.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	Optional<Department> findByDepartmentCode(String departmentCode);

	Optional<Department> findByName(String name);

	List<Department> findByNameContainingIgnoreCase(String name);

	boolean existsByDepartmentCode(String departmentCode);

	boolean existsByName(String name);
}