package com.tej.Worksphere.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollResponseDTO {

    private Long id;

    private Long employeeId;

    private String employeeName;

    private BigDecimal basicSalary;

    private BigDecimal hra;

    private BigDecimal allowances;

    private BigDecimal deductions;

    private BigDecimal bonus;

    private BigDecimal netSalary;

    private String salaryMonth;

    private LocalDate paymentDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}