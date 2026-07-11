package com.tej.Worksphere.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tej.Worksphere.entity.Employee;
import com.tej.Worksphere.exception.ResourceNotFoundException;
import com.tej.Worksphere.repository.EmployeeRepository;
import com.tej.Worksphere.service.FileStorageService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;


@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final EmployeeRepository employeeRepository;

    private final Path uploadPath = Paths.get("uploads");

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(uploadPath);
    }

    @Override
    public String uploadProfileImage(Long employeeId, MultipartFile file) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + employeeId));

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Please select an image.");
        }
        String fileName = System.currentTimeMillis()
                + "_"
                + file.getOriginalFilename();

        try {

            file.transferTo(uploadPath.resolve(fileName));

        } catch (Exception e) {

            throw new RuntimeException("Could not upload file.", e);
        }
        employee.setProfileImage(fileName);

        employeeRepository.save(employee);

        return fileName;

    }

    @Override
public Resource loadProfileImage(String fileName) {

    try {

        Path file = uploadPath.resolve(fileName);

        Resource resource = new UrlResource(file.toUri());

        if (resource.exists()) {
            return resource;
        }

        throw new RuntimeException("File not found.");

    } catch (MalformedURLException e) {

        throw new RuntimeException("Could not read file.", e);
    }
}
}