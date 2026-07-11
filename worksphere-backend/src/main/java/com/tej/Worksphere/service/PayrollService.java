package com.tej.Worksphere.service;

import java.util.List;

import com.tej.Worksphere.entity.Payroll;

public interface PayrollService {

    Payroll createPayroll(Payroll payroll);

    Payroll updatePayroll(Long id, Payroll payroll);

    Payroll getPayrollById(Long id);

    List<Payroll> getAllPayrolls();

    List<Payroll> getPayrollsByEmployee(Long employeeId);

    void deletePayroll(Long id);
}