package com.tej.Worksphere.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tej.Worksphere.entity.Payroll;
import com.tej.Worksphere.exception.DuplicateResourceException;
import com.tej.Worksphere.exception.ResourceNotFoundException;
import com.tej.Worksphere.repository.EmployeeRepository;
import com.tej.Worksphere.repository.PayrollRepository;
import com.tej.Worksphere.service.PayrollService;
import com.tej.Worksphere.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;


    @Override
public Payroll createPayroll(Payroll payroll) {
System.out.println("CREATE PAYROLL SERVICE CALLED");
    employeeRepository.findById(payroll.getEmployee().getId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Employee not found with id: "
                                    + payroll.getEmployee().getId()));


      payrollRepository.findByEmployeeIdAndSalaryMonth(
        payroll.getEmployee().getId(),
        payroll.getSalaryMonth())
        .ifPresent(existing -> {
            throw new DuplicateResourceException(
                    "Payroll already exists for this employee and salary month.");
        });                              
    BigDecimal netSalary = payroll.getBasicSalary()
            .add(payroll.getHra())
            .add(payroll.getAllowances())
            .add(payroll.getBonus())
            .subtract(payroll.getDeductions());

    payroll.setNetSalary(netSalary);

    return payrollRepository.save(payroll);
}

@Override
public Payroll updatePayroll(Long id, Payroll payroll) {

    Payroll existingPayroll = getPayrollById(id);

    employeeRepository.findById(payroll.getEmployee().getId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Employee not found with id: "
                                    + payroll.getEmployee().getId()));

    BigDecimal netSalary = payroll.getBasicSalary()
            .add(payroll.getHra())
            .add(payroll.getAllowances())
            .add(payroll.getBonus())
            .subtract(payroll.getDeductions());

    existingPayroll.setEmployee(payroll.getEmployee());
    existingPayroll.setBasicSalary(payroll.getBasicSalary());
    existingPayroll.setHra(payroll.getHra());
    existingPayroll.setAllowances(payroll.getAllowances());
    existingPayroll.setDeductions(payroll.getDeductions());
    existingPayroll.setBonus(payroll.getBonus());
    existingPayroll.setNetSalary(netSalary);
    existingPayroll.setSalaryMonth(payroll.getSalaryMonth());
    existingPayroll.setPaymentDate(payroll.getPaymentDate());

    return payrollRepository.save(existingPayroll);
}

@Override
@Transactional(readOnly = true)
public Payroll getPayrollById(Long id) {

    return payrollRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Payroll not found with id: " + id));
}

@Override
@Transactional(readOnly = true)
public List<Payroll> getAllPayrolls() {

    return payrollRepository.findAll();
}

@Override
@Transactional(readOnly = true)
public List<Payroll> getPayrollsByEmployee(Long employeeId) {

    employeeRepository.findById(employeeId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Employee not found with id: " + employeeId));

    return payrollRepository.findByEmployeeId(employeeId);
}

@Override
public void deletePayroll(Long id) {

    Payroll payroll = getPayrollById(id);

    payrollRepository.delete(payroll);
}
}