package org.travelagency.model.importDTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.travelagency.model.annotations.ValidEmail;
import org.travelagency.model.annotations.ValidSizeOrNull;
import org.travelagency.model.enums.EducationLevel;

public class AddCandidateDTO {

    @NotNull
    @Size(min = 2, max = 15, message = "Името трябва да съдържа между 2 и 15 символа!")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 15, message = "Фамилията трябва да съдържа между 2 и 15 символа!")
    private String lastName;

    @NotNull
    @ValidEmail(message = "Имейлът трябва да бъде във формат example@domain.com")
    private String email;

    @NotNull
    @Size(min = 7, max = 15, message = "Телефонният номер трябва да съдържа между 7 и 15 символа!")
    private String phoneNumber;

    @NotNull
    @Size(min = 3, max = 70, message = "Адресът трябва да съдържа между 3 и 70 символа!")
    private String address;

    @NotNull(message = "Трябва да изберете степен на образование!")
    @Enumerated(EnumType.STRING)
    private EducationLevel education;

    @ValidSizeOrNull(message = "Специалността трябва бъде между 10 и 80 символа, ако сте посочили висше образование!")
    private String specialty;

    @NotNull
    @Size(min = 3, max = 50, message = "Трябва да посочите поне един чужд език!")
    private String languages;

    public AddCandidateDTO() {
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

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
