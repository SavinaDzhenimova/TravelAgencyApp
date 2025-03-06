package org.travelagency.service.interfaces;

import java.time.LocalDate;

public interface EmailService {

    void sendInquiryEmail(String name, String email, String phone, String message, String excursionName);

    void sendForgotPasswordEmail(String fullName, String email, String password);

    void sendHireEmployeeEmail(String fullName, String email, String phoneNumber, String address, String education,
                               String specialty, String languages, String password, LocalDate hiredOn);

    void sendPromoteEmployeeEmail(String fullName, String email);

    void sendAddCandidateEmail(String firstName, String lastName, String email, String phoneNumber, String address,
                               String education, String specialty, String languages, LocalDate date);

    void sendAddExcursionEmail(String excursionName, String email);
}
