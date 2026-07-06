package com.tej.Worksphere.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tej.Worksphere.exception.DuplicateResourceException;
import com.tej.Worksphere.entity.PerformanceReview;
import com.tej.Worksphere.exception.DuplicateResourceException;
import com.tej.Worksphere.exception.ResourceNotFoundException;
import com.tej.Worksphere.repository.EmployeeRepository;
import com.tej.Worksphere.repository.PerformanceReviewRepository;
import com.tej.Worksphere.repository.UserRepository;
import com.tej.Worksphere.service.PerformanceReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    private final PerformanceReviewRepository performanceReviewRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

@Override
public PerformanceReview createReview(PerformanceReview review) {

    employeeRepository.findById(review.getEmployee().getId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Employee not found with id: "
                                    + review.getEmployee().getId()));

    userRepository.findById(review.getReviewer().getId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Reviewer not found with id: "
                                    + review.getReviewer().getId()));


    performanceReviewRepository
        .findByEmployeeIdAndReviewPeriod(
                review.getEmployee().getId(),
                review.getReviewPeriod())
        .ifPresent(existing -> {
            throw new DuplicateResourceException(
                    "Performance review already exists for this employee and review period.");
        });                                
    return performanceReviewRepository.save(review);
}

@Override
public PerformanceReview updateReview(Long id, PerformanceReview review) {

    PerformanceReview existingReview = getReviewById(id);

    employeeRepository.findById(review.getEmployee().getId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Employee not found with id: "
                                    + review.getEmployee().getId()));

    userRepository.findById(review.getReviewer().getId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Reviewer not found with id: "
                                    + review.getReviewer().getId()));

    existingReview.setEmployee(review.getEmployee());
    existingReview.setReviewer(review.getReviewer());
    existingReview.setReviewDate(review.getReviewDate());
    existingReview.setReviewPeriod(review.getReviewPeriod());
    existingReview.setRating(review.getRating());
    existingReview.setComments(review.getComments());

    return performanceReviewRepository.save(existingReview);
}

@Override
@Transactional(readOnly = true)
public PerformanceReview getReviewById(Long id) {

    return performanceReviewRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Performance Review not found with id: " + id));
}

@Override
@Transactional(readOnly = true)
public List<PerformanceReview> getAllReviews() {

    return performanceReviewRepository.findAll();
}

@Override
@Transactional(readOnly = true)
public List<PerformanceReview> getReviewsByEmployee(Long employeeId) {

    employeeRepository.findById(employeeId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Employee not found with id: " + employeeId));

    return performanceReviewRepository.findByEmployeeId(employeeId);
}

@Override
public void deleteReview(Long id) {

    PerformanceReview review = getReviewById(id);

    performanceReviewRepository.delete(review);
}
}