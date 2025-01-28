package org.travelagency.model.exportDTO;

public class DestinationViewInfo {

    DestinationViewDTO destination;

    CountryViewDTO country;

    EmbassyViewDTO embassy;

    public DestinationViewInfo() {
    }

    public DestinationViewInfo(DestinationViewDTO destination, CountryViewDTO country, EmbassyViewDTO embassy) {
        this.destination = destination;
        this.country = country;
        this.embassy = embassy;
    }

    public DestinationViewDTO getDestination() {
        return destination;
    }

    public void setDestination(DestinationViewDTO destination) {
        this.destination = destination;
    }

    public CountryViewDTO getCountry() {
        return country;
    }

    public void setCountry(CountryViewDTO country) {
        this.country = country;
    }

    public EmbassyViewDTO getEmbassy() {
        return embassy;
    }

    public void setEmbassy(EmbassyViewDTO embassy) {
        this.embassy = embassy;
    }
}
