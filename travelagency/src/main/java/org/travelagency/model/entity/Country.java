package org.travelagency.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String capital;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false, name = "time_difference")
    private String timeDifference;

    @ManyToOne(optional = false)
    @JoinColumn(name = "continent_id", referencedColumnName = "id")
    private Continent continent;

    @OneToOne(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Embassy embassy;

    @OneToOne(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Destination destination;

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
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

    public Embassy getEmbassy() {
        return embassy;
    }

    public void setEmbassy(Embassy embassy) {
        this.embassy = embassy;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}