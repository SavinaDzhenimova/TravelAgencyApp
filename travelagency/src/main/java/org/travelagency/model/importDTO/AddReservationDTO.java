package org.travelagency.model.importDTO;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.travelagency.model.annotations.ValidEmail;
import org.travelagency.model.enums.PaymentModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class AddReservationDTO {

    @NotNull
    private String excursionName;

    @NotNull
    @FutureOrPresent
    private LocalDate excursionDate;

    @NotNull
    @Positive(message = "Броят на туристите, които ще пътуват не може да бъде по-малък от 1!")
    private int touristsCount;

    @NotNull
    @Size(min = 5, max = 50, message = "Името трябва да съдържа между 5 и 50 символа!")
    private String fullName;

    @NotNull
    @Size(min = 7, max = 15, message = "Телефонният номер трябва да съдържа между 7 и 15 символа!")
    private String phoneNumber;

    @NotNull
    @ValidEmail(message = "Имейлът трябва да бъде във формат example@domain.com")
    private String email;

    @NotNull(message = "Трябва да изберете метод на плащане!")
    private PaymentModel payment;

    @NotEmpty(message = "Трябва да въведете три имена на всички туристи, които ще пътуват!")
    private Set<@NotEmpty(message = "Трябва да въведете три имена на всички туристи!") String> touristNames;

    private String comments;

    public AddReservationDTO() {
        this.touristsCount = 1;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public LocalDate getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(LocalDate excursionDate) {
        this.excursionDate = excursionDate;
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

    public PaymentModel getPayment() {
        return payment;
    }

    public void setPayment(PaymentModel payment) {
        this.payment = payment;
    }

    public Set<String> getTouristNames() {
        return touristNames;
    }

    public void setTouristNames(Set<String> touristNames) {
        this.touristNames = touristNames;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
