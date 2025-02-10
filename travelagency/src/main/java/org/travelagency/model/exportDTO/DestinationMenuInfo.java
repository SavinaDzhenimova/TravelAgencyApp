package org.travelagency.model.exportDTO;

import java.util.List;

public class DestinationMenuInfo {

    List<DestinationMenuDTO> destinations;

    public DestinationMenuInfo(List<DestinationMenuDTO> destinations) {
        this.destinations = destinations;
    }

    public List<DestinationMenuDTO> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<DestinationMenuDTO> destinations) {
        this.destinations = destinations;
    }
}
