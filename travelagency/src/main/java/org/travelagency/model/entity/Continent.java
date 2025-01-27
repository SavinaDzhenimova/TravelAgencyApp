package org.travelagency.model.entity;

import jakarta.persistence.*;
import org.travelagency.model.enums.ContinentName;

import java.util.List;

@Entity
@Table(name = "continents")
public class Continent extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ContinentName continentName;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "continent", fetch = FetchType.LAZY)
    private List<Country> countries;

    public ContinentName getContinentName() {
        return continentName;
    }

    public void setContinentName(ContinentName continentName) {
        this.continentName = continentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
