package com.tej.Worksphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tej.Worksphere.entity.Leave;
import com.tej.Worksphere.entity.LeaveStatus;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeId(Long employeeId);

    List<Leave> findByStatus(LeaveStatus status);

    List<Leave> findByEmployeeIdAndStatus(Long employeeId, LeaveStatus status);

    List<Leave> findByApprovedById(Long userId);

    List<Leave> findByEmployeeIdOrderByStartDateDesc(Long employeeId);

}
