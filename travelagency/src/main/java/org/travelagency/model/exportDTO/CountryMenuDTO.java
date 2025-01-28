package org.travelagency.model.exportDTO;

import org.travelagency.model.enums.ContinentName;

public class CountryMenuDTO {

    private String name;

    private ContinentName continentName;

    public CountryMenuDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContinentName getContinentName() {
        return continentName;
    }

    public void setContinentName(ContinentName continentName) {
        this.continentName = continentName;
    }
}
