package org.travelagency.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import org.travelagency.model.enums.PaymentModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    @Column(nullable = false, name = "tourists_count")
    private int touristsCount;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "payment_model")
    private PaymentModel paymentModel;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, name = "excursion_date")
    @FutureOrPresent
    private LocalDate excursionDate;

    @Column
    private String comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "excursion_id", referencedColumnName = "id")
    private Excursion excursion;

    @Column(name = "tourist_names")
    private Set<String> touristNames;

    public Reservation() {
        this.touristNames = new HashSet<>();
    }

    public int getTouristsCount() {
        return touristsCount;
    }

    public void setTouristsCount(int touristsCount) {
        this.touristsCount = touristsCount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PaymentModel getPaymentModel() {
        return paymentModel;
    }

    public void setPaymentModel(PaymentModel paymentModel) {
        this.paymentModel = paymentModel;
    }

    public Set<String> getTouristNames() {
        return touristNames;
    }

    public void setTouristNames(Set<String> touristNames) {
        this.touristNames = touristNames;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(LocalDate excursionDate) {
        this.excursionDate = excursionDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Excursion getExcursion() {
        return excursion;
    }

    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;
    }
}
