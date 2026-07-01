package com.tej.Worksphere.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.tej.Worksphere.entity.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponseDTO {

    private Long id;

    private Long employeeId;

    private String employeeName;

    private LocalDate date;

    private LocalTime checkIn;

    private LocalTime checkOut;

    private AttendanceStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
