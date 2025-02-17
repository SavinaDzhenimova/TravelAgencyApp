package org.travelagency.service.interfaces;

public interface EmailService {

    void sendHireEmployeeEmail(String fullName, String phoneNumber, String education, String email,
                               String address, String specialty, String languages);

    void sendPromoteEmployeeEmail(String fullName, String email);

    void sendAddCandidateEmail(String firstName, String lastName, String email, String phoneNumber,
                               String address, String education, String specialty, String languages);

    void sendAddExcursionEmail(String excursionName, String email);
}
