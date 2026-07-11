package com.tej.Worksphere.service;

public interface EmailService {

    void sendSimpleEmail(
            String to,
            String subject,
            String body);

    void sendLeaveApprovedEmail(
            String to,
            String employeeName,
            String leaveType);

    void sendLeaveRejectedEmail(
            String to,
            String employeeName,
            String leaveType);

}