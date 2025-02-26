package org.travelagency.service.events;

import org.springframework.context.ApplicationEvent;

public class HireEmployeeEvent extends ApplicationEvent {

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private String education;

    private String specialty;

    private String languages;

    private String password;

    public HireEmployeeEvent(Object source, String fullName, String email, String phoneNumber, String address,
                             String education, String specialty, String languages, String password) {
        super(source);
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.education = education;
        this.specialty = specialty;
        this.languages = languages;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
