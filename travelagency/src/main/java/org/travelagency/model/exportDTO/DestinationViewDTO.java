package org.travelagency.model.exportDTO;

import org.travelagency.model.enums.ContinentName;

public class DestinationViewDTO {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private String visaRequirements;

    private String timeToVisit;

    private String goodToKnow;

    private String capital;

    private String currency;

    private String timeDifference;

    public DestinationViewDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}