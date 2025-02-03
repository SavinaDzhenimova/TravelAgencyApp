package org.travelagency.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.travelagency.model.entity.Language;
import org.travelagency.model.enums.EducationLevel;

import java.util.Collection;
import java.util.Set;

public class UserDetailsDTO extends User {

    private Long id;

    private String fullName;

    private String email;

    private String address;

    private String phoneNumber;

    private EducationLevel education;

    private String specialty;

    private Set<Language> languages;

    public UserDetailsDTO(String username,
                          String password,
                          Collection<? extends GrantedAuthority> authorities,
                          Long id,
                          String fullName,
                          String email,
                          String address,
                          String phoneNumber,
                          EducationLevel education,
                          String specialty,
                          Set<Language> languages) {
        super(username, password, authorities);
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.education = education;
        this.specialty = specialty;
        this.languages = languages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }
}