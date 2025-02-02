package org.travelagency.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Table
@Entity(name = "employees")
public class Employee extends BaseInfo {

    @Column(nullable = false)
    @Size(min = 5, max = 40)
    private String fullName;

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
