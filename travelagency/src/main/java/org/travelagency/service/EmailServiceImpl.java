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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
    public void sendHireEmployeeEmail(String fullName, String email, String phoneNumber, String address, String education,
                                      String specialty, String languages, String username, String password) {
        Map<String, Object> variables = Map.of(
                "fullName", fullName,
                "email", email,
                "phoneNumber", phoneNumber,
                "address", address,
                "education", education,
                "specialty", specialty,
                "languages", languages,
                "username", username,
                "password", password
        );

        String content = generateEmailContent("/email/hire-email", variables);
        sendEmail(email, "Добре дошли в екипа на Sunrise Travel Agency Bulgaria!", content);
    }

    @Override
    public void sendPromoteEmployeeEmail(String fullName, String email) {
        Map<String, Object> variables = Map.of("fullName", fullName);

        String content = generateEmailContent("/email/promote-email", variables);
        sendEmail(email, "Повишение в екипа на Sunrise Travel Agency Bulgaria!", content);
    }

    @Override
    public void sendAddCandidateEmail(String firstName, String lastName, String email, String phoneNumber,
                                      String address, String education, String specialty, String languages) {
        Map<String, Object> variables = Map.of(
                "fullName", firstName + " " + lastName,
                "firstName", firstName,
                "lastName", lastName,
                "email", email,
                "phoneNumber", phoneNumber,
                "address", address,
                "education", education,
                "specialty", specialty,
                "languages", languages
        );

        String content = generateEmailContent("/email/candidate-email", variables);
        sendEmail(email, "Успешно приета кандидатура в Sunrise Travel Agency Bulgaria!", content);
    }

    @Override
    public void sendAddExcursionEmail(String excursionName, String email) {
        String excursionUrl = "http://localhost:8090/excursions/excursion-details/"
                + URLEncoder.encode(excursionName, StandardCharsets.UTF_8);

        Map<String, Object> variables = Map.of(
                "excursionName", excursionName,
                "excursionUrl", excursionUrl);

        String content = generateEmailContent("/email/add-excursion-email", variables);
        sendEmail(email, "Нова екскурзия в Sunrise Travel Agency Bulgaria!", content);
    }

    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(this.email);
            mimeMessageHelper.setReplyTo(this.email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Грешка при изпращане на имейл: " + e.getMessage(), e);
        }
    }

    private String generateEmailContent(String templatePath, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templatePath, context);
    }
}