package com.tej.Worksphere.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.tej.Worksphere.dto.PerformanceReviewRequestDTO;
import com.tej.Worksphere.dto.PerformanceReviewResponseDTO;
import com.tej.Worksphere.entity.Employee;
import com.tej.Worksphere.entity.PerformanceReview;
import com.tej.Worksphere.entity.User;
import com.tej.Worksphere.service.EmployeeService;
import com.tej.Worksphere.service.PerformanceReviewService;
import com.tej.Worksphere.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/performance")
public class PerformanceReviewController {

    private final PerformanceReviewService performanceReviewService;
    private final EmployeeService employeeService;
    private final UserService userService;


    @PostMapping
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<PerformanceReviewResponseDTO> createReview(
        @Valid @RequestBody PerformanceReviewRequestDTO requestDTO) {

    PerformanceReview review = mapToEntity(requestDTO);

    PerformanceReview savedReview =
            performanceReviewService.createReview(review);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapToResponseDTO(savedReview));
}

@GetMapping
@PreAuthorize("isAuthenticated()")
public ResponseEntity<List<PerformanceReviewResponseDTO>> getAllReviews() {

    List<PerformanceReviewResponseDTO> reviews =
            performanceReviewService.getAllReviews()
                    .stream()
                    .map(this::mapToResponseDTO)
                    .toList();

    return ResponseEntity.ok(reviews);
}

@GetMapping("/{id}")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<PerformanceReviewResponseDTO> getReviewById(
        @PathVariable Long id) {

    PerformanceReview review =
            performanceReviewService.getReviewById(id);

    return ResponseEntity.ok(mapToResponseDTO(review));
}

@GetMapping("/employee/{employeeId}")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<List<PerformanceReviewResponseDTO>>
getReviewsByEmployee(@PathVariable Long employeeId) {

    List<PerformanceReviewResponseDTO> reviews =
            performanceReviewService
                    .getReviewsByEmployee(employeeId)
                    .stream()
                    .map(this::mapToResponseDTO)
                    .toList();

    return ResponseEntity.ok(reviews);
}

@PutMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<PerformanceReviewResponseDTO> updateReview(
        @PathVariable Long id,
        @Valid @RequestBody PerformanceReviewRequestDTO requestDTO) {

    PerformanceReview review = mapToEntity(requestDTO);

    PerformanceReview updatedReview =
            performanceReviewService.updateReview(id, review);

    return ResponseEntity.ok(mapToResponseDTO(updatedReview));
}

@DeleteMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<Void> deleteReview(@PathVariable Long id) {

    performanceReviewService.deleteReview(id);

    return ResponseEntity.noContent().build();
}

private PerformanceReview mapToEntity(
        PerformanceReviewRequestDTO requestDTO) {

    Employee employee =
            employeeService.getEmployeeById(requestDTO.getEmployeeId());

    User reviewer =
            userService.getUserById(requestDTO.getReviewerId());

    PerformanceReview review = new PerformanceReview();

    review.setEmployee(employee);
    review.setReviewer(reviewer);
    review.setReviewDate(requestDTO.getReviewDate());
    review.setReviewPeriod(requestDTO.getReviewPeriod());
    review.setRating(requestDTO.getRating());
    review.setComments(requestDTO.getComments());

    return review;
}

private PerformanceReviewResponseDTO mapToResponseDTO(
        PerformanceReview review) {

    Employee employee = review.getEmployee();
    User reviewer = review.getReviewer();

    return PerformanceReviewResponseDTO.builder()
            .id(review.getId())

            .employeeId(employee != null ? employee.getId() : null)
            .employeeName(employee != null
                    ? employee.getFirstName() + " " + employee.getLastName()
                    : null)

            .reviewerId(reviewer != null ? reviewer.getId() : null)
            .reviewerName(reviewer != null
                    ? reviewer.getFirstName() + " " + reviewer.getLastName()
                    : null)

            .reviewDate(review.getReviewDate())
            .reviewPeriod(review.getReviewPeriod())
            .rating(review.getRating())
            .comments(review.getComments())

            .createdAt(review.getCreatedAt())
            .updatedAt(review.getUpdatedAt())

            .build();
}
}