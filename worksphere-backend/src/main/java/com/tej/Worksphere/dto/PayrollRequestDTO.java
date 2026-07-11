package com.tej.Worksphere.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
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
public class PayrollRequestDTO {

    @NotNull(message = "Employee id is required")
    private Long employeeId;

    @NotNull(message = "Basic salary is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Basic salary must be greater than or equal to 0")
    private BigDecimal basicSalary;

    @NotNull(message = "HRA is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "HRA must be greater than or equal to 0")
    private BigDecimal hra;

    @NotNull(message = "Allowances are required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Allowances must be greater than or equal to 0")
    private BigDecimal allowances;

    @NotNull(message = "Deductions are required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Deductions must be greater than or equal to 0")
    private BigDecimal deductions;

    @NotNull(message = "Bonus is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Bonus must be greater than or equal to 0")
    private BigDecimal bonus;

    @NotBlank(message = "Salary month is required")
    private String salaryMonth;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;
}