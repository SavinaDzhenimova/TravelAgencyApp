package org.travelagency.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.travelagency.model.annotations.ValidEmail;
import org.travelagency.model.enums.EducationLevel;

import java.util.HashSet;
import java.util.Set;

@Table
@Entity(name = "employees")
public class Employee extends BaseEntity {

    @Column(nullable = false)
    @Size(min = 5, max = 40)
    private String fullName;

    @Column(nullable = false)
    @ValidEmail
    private String email;

    @Column(nullable = false)
    @Size(min = 7, max = 15)
    private String phoneNumber;

    @Column(nullable = false)
    @Size(min = 3, max = 70)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationLevel education;

    @Column
    @Size(min = 10, max = 80)
    private String specialty;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employees_languages",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id"))
    private Set<Language> languages;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 20)
    private String username;

    @Column(nullable = false)
    @Size(min = 8)
    private String password;

    public Employee() {
        this.languages = new HashSet<>();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
