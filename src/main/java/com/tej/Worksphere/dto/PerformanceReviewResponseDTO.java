package com.tej.Worksphere.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tej.Worksphere.entity.PerformanceRating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReviewResponseDTO {

    private Long id;

    private Long employeeId;
    private String employeeName;

    private Long reviewerId;
    private String reviewerName;

    private LocalDate reviewDate;

    private String reviewPeriod;

    private PerformanceRating rating;

    private String comments;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}