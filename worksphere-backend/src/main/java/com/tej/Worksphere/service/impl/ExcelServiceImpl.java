package com.tej.Worksphere.service.impl;

import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.tej.Worksphere.repository.EmployeeRepository;
import com.tej.Worksphere.service.ExcelService;

import lombok.RequiredArgsConstructor;
import java.util.List;

import com.tej.Worksphere.entity.Employee;
@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final EmployeeRepository employeeRepository;

    @Override
public byte[] exportEmployees() {

    try (
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {

        Sheet sheet = workbook.createSheet("Employees");
Row header = sheet.createRow(0);

header.createCell(0).setCellValue("ID");
header.createCell(1).setCellValue("Employee Code");
header.createCell(2).setCellValue("First Name");
header.createCell(3).setCellValue("Last Name");
header.createCell(4).setCellValue("Email");
header.createCell(5).setCellValue("Gender");
header.createCell(6).setCellValue("Hire Date");
header.createCell(7).setCellValue("Status");
header.createCell(8).setCellValue("Department");
List<Employee> employees = employeeRepository.findAll();
int rowNum = 1;

for (Employee employee : employees) {

    Row row = sheet.createRow(rowNum++);

    row.createCell(0).setCellValue(employee.getId());

    row.createCell(1).setCellValue(employee.getEmployeeCode());

    row.createCell(2).setCellValue(employee.getFirstName());

    row.createCell(3).setCellValue(employee.getLastName());

    row.createCell(4).setCellValue(
            employee.getEmail() != null
                    ? employee.getEmail()
                    : "");

    row.createCell(5).setCellValue(
            employee.getGender() != null
                    ? employee.getGender()
                    : "");

    row.createCell(6).setCellValue(
            employee.getHireDate() != null
                    ? employee.getHireDate().toString()
                    : "");

    row.createCell(7).setCellValue(
            employee.getStatus() != null
                    ? employee.getStatus()
                    : "");

    row.createCell(8).setCellValue(
            employee.getDepartment() != null
                    ? employee.getDepartment().getName()
                    : "");
}


for (int i = 0; i < 9; i++) {
    sheet.autoSizeColumn(i);
}    workbook.write(out);

        return out.toByteArray();

    } catch (Exception e) {

        throw new RuntimeException("Failed to export employees.", e);
    }
}
}