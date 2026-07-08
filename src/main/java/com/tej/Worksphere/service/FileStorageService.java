package com.tej.Worksphere.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
public interface FileStorageService {

    String uploadProfileImage(Long employeeId, MultipartFile file);
Resource loadProfileImage(String fileName);
}