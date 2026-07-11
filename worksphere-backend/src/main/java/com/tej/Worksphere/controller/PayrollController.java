package com.tej.Worksphere.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.tej.Worksphere.dto.PayrollRequestDTO;
import com.tej.Worksphere.dto.PayrollResponseDTO;
import com.tej.Worksphere.entity.Employee;
import com.tej.Worksphere.entity.Payroll;
import com.tej.Worksphere.service.EmployeeService;
import com.tej.Worksphere.service.PayrollService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.tej.Worksphere.service.PdfService;
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payrolls")
public class PayrollController {

    private final PayrollService payrollService;
    private final EmployeeService employeeService;
private final PdfService pdfService;

    @PostMapping
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<PayrollResponseDTO> createPayroll(
        @Valid @RequestBody PayrollRequestDTO requestDTO) {
System.out.println("CREATE PAYROLL CONTROLLER CALLED");
    Payroll payroll = mapToEntity(requestDTO);

    Payroll savedPayroll = payrollService.createPayroll(payroll);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapToResponseDTO(savedPayroll));
}

@GetMapping
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<List<PayrollResponseDTO>> getAllPayrolls() {

    List<PayrollResponseDTO> payrolls = payrollService.getAllPayrolls()
            .stream()
            .map(this::mapToResponseDTO)
            .toList();

    return ResponseEntity.ok(payrolls);
}

@GetMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<PayrollResponseDTO> getPayrollById(
        @PathVariable Long id) {

    Payroll payroll = payrollService.getPayrollById(id);

    return ResponseEntity.ok(mapToResponseDTO(payroll));
}

@GetMapping("/employee/{employeeId}")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<List<PayrollResponseDTO>> getPayrollsByEmployee(
        @PathVariable Long employeeId) {

    List<PayrollResponseDTO> payrolls = payrollService
            .getPayrollsByEmployee(employeeId)
            .stream()
            .map(this::mapToResponseDTO)
            .toList();

    return ResponseEntity.ok(payrolls);
}

@PutMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<PayrollResponseDTO> updatePayroll(
        @PathVariable Long id,
        @Valid @RequestBody PayrollRequestDTO requestDTO) {

    Payroll payroll = mapToEntity(requestDTO);

    Payroll updatedPayroll = payrollService.updatePayroll(id, payroll);

    return ResponseEntity.ok(mapToResponseDTO(updatedPayroll));
}

@DeleteMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<Void> deletePayroll(@PathVariable Long id) {

    payrollService.deletePayroll(id);

    return ResponseEntity.noContent().build();
}

@GetMapping("/{id}/payslip")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public ResponseEntity<byte[]> downloadPayslip(
        @PathVariable Long id) {
System.out.println("downloadPayslip() called");
    byte[] pdf = pdfService.generatePayrollPdf(id);

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=payslip_" + id + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
}



private Payroll mapToEntity(PayrollRequestDTO requestDTO) {

    Employee employee =
            employeeService.getEmployeeById(requestDTO.getEmployeeId());

    Payroll payroll = new Payroll();

    payroll.setEmployee(employee);
    payroll.setBasicSalary(requestDTO.getBasicSalary());
    payroll.setHra(requestDTO.getHra());
    payroll.setAllowances(requestDTO.getAllowances());
    payroll.setDeductions(requestDTO.getDeductions());
    payroll.setBonus(requestDTO.getBonus());
    payroll.setSalaryMonth(requestDTO.getSalaryMonth());
    payroll.setPaymentDate(requestDTO.getPaymentDate());

    return payroll;
}

private PayrollResponseDTO mapToResponseDTO(Payroll payroll) {

    Employee employee = payroll.getEmployee();

    return PayrollResponseDTO.builder()
            .id(payroll.getId())
            .employeeId(employee != null ? employee.getId() : null)
            .employeeName(employee != null
                    ? employee.getFirstName() + " " + employee.getLastName()
                    : null)
            .basicSalary(payroll.getBasicSalary())
            .hra(payroll.getHra())
            .allowances(payroll.getAllowances())
            .deductions(payroll.getDeductions())
            .bonus(payroll.getBonus())
            .netSalary(payroll.getNetSalary())
            .salaryMonth(payroll.getSalaryMonth())
            .paymentDate(payroll.getPaymentDate())
            .createdAt(payroll.getCreatedAt())
            .updatedAt(payroll.getUpdatedAt())
            .build();
}
}