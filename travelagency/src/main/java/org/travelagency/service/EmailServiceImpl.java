package org.travelagency.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.travelagency.service.interfaces.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final String email;

    public EmailServiceImpl(TemplateEngine templateEngine, JavaMailSender javaMailSender,
                            @Value("${mail.sunrise_travel}") String email) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.email = email;
    }

    @Override
    public void sendHireEmployeeEmail(String fullName, String email, String phoneNumber, String address,
                                      String education, String specialty, String languages) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setFrom(this.email);
            mimeMessageHelper.setReplyTo(this.email);
            mimeMessageHelper.setSubject("Добре дошли в екипа на Sunrise Travel Agency Bulgaria!");
            mimeMessageHelper.setText(generateHireEmployeeEmail(fullName, email, phoneNumber, address, education,
                    specialty, languages), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateHireEmployeeEmail(String fullName, String email, String phoneNumber, String address,
                                             String education, String specialty, String languages) {

        Context context = new Context();

        context.setVariable("fullName", fullName);
        context.setVariable("email", email);
        context.setVariable("phoneNumber", phoneNumber);
        context.setVariable("address", address);
        context.setVariable("education", education);
        context.setVariable("specialty", specialty);
        context.setVariable("languages", languages);

        return templateEngine.process("/email/hire-email", context);
    }

    @Override
    public void promoteEmployeeEmail(String fullName, String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setFrom(this.email);
            mimeMessageHelper.setReplyTo(this.email);
            mimeMessageHelper.setSubject("Повишение в екипа на Sunrise Travel Agency Bulgaria!");
            mimeMessageHelper.setText(generatePromoteEmployeeEmail(fullName), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generatePromoteEmployeeEmail(String fullName) {

        Context context = new Context();

        context.setVariable("fullName", fullName);

        return templateEngine.process("/email/promote-email", context);
    }
}
