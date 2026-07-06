package com.tej.Worksphere.service;

import java.util.List;

import com.tej.Worksphere.entity.PerformanceReview;

public interface PerformanceReviewService {

    PerformanceReview createReview(PerformanceReview review);

    PerformanceReview updateReview(Long id, PerformanceReview review);

    PerformanceReview getReviewById(Long id);

    List<PerformanceReview> getAllReviews();

    List<PerformanceReview> getReviewsByEmployee(Long employeeId);

    void deleteReview(Long id);

}