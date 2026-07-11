package com.tej.Worksphere.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payrolls")
public class Payroll extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "basic_salary", nullable = false, precision = 19, scale = 2)
    private BigDecimal basicSalary;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal hra;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal allowances;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal deductions;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal bonus;

    @Column(name = "net_salary", nullable = false, precision = 19, scale = 2)
    private BigDecimal netSalary;

    @Column(name = "salary_month", nullable = false, length = 20)
    private String salaryMonth;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;
}