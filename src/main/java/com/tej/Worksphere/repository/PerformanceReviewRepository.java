package com.tej.Worksphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.tej.Worksphere.entity.PerformanceReview;

@Repository
public interface PerformanceReviewRepository
        extends JpaRepository<PerformanceReview, Long> {

    List<PerformanceReview> findByEmployeeId(Long employeeId);

    List<PerformanceReview> findByReviewerId(Long reviewerId);

    List<PerformanceReview> findByReviewPeriod(String reviewPeriod);

    Optional<PerformanceReview> findByEmployeeIdAndReviewPeriod(
        Long employeeId,
        String reviewPeriod);

}