package org.travelagency.service.events;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;

public class AddCandidateEvent extends ApplicationEvent {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String education;

    private String specialty;

    private String languages;

    private LocalDate date;

    public AddCandidateEvent(Object source, String firstName, String lastName, String email, String phoneNumber,
                             String address, String education, String specialty, String languages, LocalDate date) {
        super(source);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.education = education;
        this.specialty = specialty;
        this.languages = languages;
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}