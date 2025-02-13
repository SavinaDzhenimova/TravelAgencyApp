package org.travelagency.model.exportDTO.country;

import java.util.ArrayList;
import java.util.List;

public class CountryMenuInfo {

    private List<CountryMenuDTO> europeCountries;

    private List<CountryMenuDTO> africaCountries;

    private List<CountryMenuDTO> asiaCountries;

    private List<CountryMenuDTO> southAmericaCountries;

    private List<CountryMenuDTO> northAmericaCountries;

    private List<CountryMenuDTO> australiaCountries;

    public CountryMenuInfo() {
        this.europeCountries = new ArrayList<>();
        this.africaCountries = new ArrayList<>();
        this.asiaCountries = new ArrayList<>();
        this.southAmericaCountries = new ArrayList<>();
        this.northAmericaCountries = new ArrayList<>();
        this.australiaCountries = new ArrayList<>();
    }

    public CountryMenuInfo(List<CountryMenuDTO> europeCountries, List<CountryMenuDTO> africaCountries,
                           List<CountryMenuDTO> asiaCountries, List<CountryMenuDTO> southAmericaCountries,
                           List<CountryMenuDTO> northAmericaCountries, List<CountryMenuDTO> australiaCountries) {
        this.europeCountries = europeCountries;
        this.africaCountries = africaCountries;
        this.asiaCountries = asiaCountries;
        this.southAmericaCountries = southAmericaCountries;
        this.northAmericaCountries = northAmericaCountries;
        this.australiaCountries = australiaCountries;
    }

    public List<CountryMenuDTO> getEuropeCountries() {
        return europeCountries;
    }

    public void setEuropeCountries(List<CountryMenuDTO> europeCountries) {
        this.europeCountries = europeCountries;
    }

    public List<CountryMenuDTO> getAfricaCountries() {
        return africaCountries;
    }

    public void setAfricaCountries(List<CountryMenuDTO> africaCountries) {
        this.africaCountries = africaCountries;
    }

    public List<CountryMenuDTO> getAsiaCountries() {
        return asiaCountries;
    }

    public void setAsiaCountries(List<CountryMenuDTO> asiaCountries) {
        this.asiaCountries = asiaCountries;
    }

    public List<CountryMenuDTO> getSouthAmericaCountries() {
        return southAmericaCountries;
    }

    public void setSouthAmericaCountries(List<CountryMenuDTO> southAmericaCountries) {
        this.southAmericaCountries = southAmericaCountries;
    }

    public List<CountryMenuDTO> getNorthAmericaCountries() {
        return northAmericaCountries;
    }

    public void setNorthAmericaCountries(List<CountryMenuDTO> northAmericaCountries) {
        this.northAmericaCountries = northAmericaCountries;
    }

    public List<CountryMenuDTO> getAustraliaCountries() {
        return australiaCountries;
    }

    public void setAustraliaCountries(List<CountryMenuDTO> australiaCountries) {
        this.australiaCountries = australiaCountries;
    }
}
