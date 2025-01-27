package org.travelagency.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "destinations")
public class Destination extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "visa_requirements", columnDefinition = "TEXT")
    private String visaRequirements;

    @Column(nullable = false, name = "time_to_visit", columnDefinition = "TEXT")
    private String timeToVisit;

    @Column(nullable = false, name = "good_to_know", columnDefinition = "TEXT")
    private String goodToKnow;

    @OneToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<Excursion> excursions;

    public Destination() {
        this.excursions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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

    public List<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(List<Excursion> excursions) {
        this.excursions = excursions;
    }
}