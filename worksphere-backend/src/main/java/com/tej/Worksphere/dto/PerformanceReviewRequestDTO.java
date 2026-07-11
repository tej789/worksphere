package com.tej.Worksphere.dto;

import java.time.LocalDate;

import com.tej.Worksphere.entity.PerformanceRating;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReviewRequestDTO {

    @NotNull(message = "Employee id is required")
    private Long employeeId;

    @NotNull(message = "Reviewer id is required")
    private Long reviewerId;

    @NotNull(message = "Review date is required")
    private LocalDate reviewDate;

    @NotBlank(message = "Review period is required")
    private String reviewPeriod;

    @NotNull(message = "Rating is required")
    private PerformanceRating rating;

    @NotBlank(message = "Comments are required")
    private String comments;
}