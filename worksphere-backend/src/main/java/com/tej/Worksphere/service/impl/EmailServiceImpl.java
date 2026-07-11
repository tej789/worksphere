package com.tej.Worksphere.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tej.Worksphere.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendSimpleEmail(
            String to,
            String subject,
            String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Override
    public void sendLeaveApprovedEmail(
            String to,
            String employeeName,
            String leaveType) {

        String subject = "Leave Request Approved";

        String body = """
                Dear %s,

                Congratulations!

                Your %s leave request has been approved.

                Regards,
                WorkSphere HRMS
                """.formatted(employeeName, leaveType);

        sendSimpleEmail(to, subject, body);
    }

    @Override
    public void sendLeaveRejectedEmail(
            String to,
            String employeeName,
            String leaveType) {

        String subject = "Leave Request Rejected";

        String body = """
                Dear %s,

                We regret to inform you that your %s leave request has been rejected.

                Please contact the HR department for more information.

                Regards,
                WorkSphere HRMS
                """.formatted(employeeName, leaveType);

        sendSimpleEmail(to, subject, body);
    }
}