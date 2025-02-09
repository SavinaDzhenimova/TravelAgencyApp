package org.travelagency.model.importDTO;

import jakarta.validation.constraints.NotNull;

public class AddDestinationDTO {

    // Country info
    @NotNull
    private String countryName;

    @NotNull
    private String capital;

    @NotNull
    private String currency;

    @NotNull
    private String timeDifference;

    @NotNull
    private String continent;

    // Embassy info
    @NotNull
    private String address;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String fax;

    @NotNull
    private String dutyPhone;

    @NotNull
    private String email;

    @NotNull
    private String webpage;

    // Destination info
    @NotNull
    private String description;

    @NotNull
    private String imageUrl;

    @NotNull
    private String visaRequirements;

    @NotNull
    private String timeToVisit;

    @NotNull
    private String goodToKnow;

    public AddDestinationDTO() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDutyPhone() {
        return dutyPhone;
    }

    public void setDutyPhone(String dutyPhone) {
        this.dutyPhone = dutyPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVisaRequirements() {
        return visaRequirements;
    }

    public void setVisaRequirements(String visaRequirements) {
        this.visaRequirements = visaRequirements;
    }

    public String getTimeToVisit() {
        return timeToVisit;
    }

    public void setTimeToVisit(String timeToVisit) {
        this.timeToVisit = timeToVisit;
    }

    public String getGoodToKnow() {
        return goodToKnow;
    }

    public void setGoodToKnow(String goodToKnow) {
        this.goodToKnow = goodToKnow;
    }
}
