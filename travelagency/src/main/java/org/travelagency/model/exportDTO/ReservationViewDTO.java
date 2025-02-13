package org.travelagency.model.exportDTO;

import java.util.List;

public class ReservationViewDTO {

    private String excursionName;

    private int touristsCount;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String payment;

    private String comments;

    private List<String> touristNames;

    public ReservationViewDTO() {
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getTouristNames() {
        return touristNames;
    }

    public void setTouristNames(List<String> touristNames) {
        this.touristNames = touristNames;
    }
}
