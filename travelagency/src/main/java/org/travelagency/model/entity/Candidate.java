package org.travelagency.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.travelagency.model.annotations.ValidEmail;
import org.travelagency.model.enums.Education;

import java.util.Set;

@Table
@Entity(name = "candidates")
public class Candidate extends BaseEntity {

    @Column(nullable = false)
    @Size(min = 2, max = 15)
    private String firstName;

    @Column(nullable = false)
    @Size(min = 2, max = 15)
    private String lastName;

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
    private Education education;

    @Column
    @Size(min = 10, max = 80)
    private String specialty;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "candidates_languages",
            joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id"))
    private Set<Language> languages;

    public Candidate() {
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

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
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
