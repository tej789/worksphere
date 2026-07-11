package com.tej.Worksphere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tej.Worksphere.entity.Department;
import com.tej.Worksphere.entity.Employee;
import com.tej.Worksphere.entity.User;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByEmployeeCode(String employeeCode);

	Optional<Employee> findByUser(User user);

	Optional<Employee> findByUser_Id(Long userId);

	List<Employee> findByDepartment(Department department);

	List<Employee> findByDepartment_Id(Long departmentId);

	List<Employee> findByStatus(String status);

	List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

	boolean existsByEmployeeCode(String employeeCode);
}