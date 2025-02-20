package org.travelagency.service.interfaces;

public interface EmailService {

    void sendInquiryEmail(String name, String email, String phone, String message, String excursionName);

    void sendHireEmployeeEmail(String fullName, String email, String phoneNumber, String address, String education,
                               String specialty, String languages, String username, String password);

    void sendPromoteEmployeeEmail(String fullName, String email);

    void sendAddCandidateEmail(String firstName, String lastName, String email, String phoneNumber,
                               String address, String education, String specialty, String languages);

    void sendAddExcursionEmail(String excursionName, String email);
}
