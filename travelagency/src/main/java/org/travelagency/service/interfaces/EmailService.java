package org.travelagency.service.interfaces;

public interface EmailService {

    void sendHireEmployeeEmail(String fullName, String phoneNumber, String education, String email,
                               String address, String specialty, String languages);
}
