package org.travelagency.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.travelagency.model.annotations.ValidEmail;
import org.travelagency.model.annotations.ValidSizeOrNull;
import org.travelagency.model.enums.EducationLevel;

@MappedSuperclass
public class BaseInfo extends BaseEntity {

    @Column(nullable = false, unique = true)
    @ValidEmail
    private String email;

    @NotNull
    @Size(min = 7, max = 15)
    private String phoneNumber;

    @NotNull
    @Size(min = 3, max = 70)
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EducationLevel education;

    @ValidSizeOrNull
    private String specialty;

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

    public EducationLevel getEducation() {
        return education;
    }

    public void setEducation(EducationLevel education) {
        this.education = education;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
