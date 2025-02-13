package org.travelagency.model.exportDTO.destination;

import java.util.List;

public class DestinationsExportListDTO {

    List<DestinationExportDTO> destinations;

    public DestinationsExportListDTO(List<DestinationExportDTO> destinations) {
        this.destinations = destinations;
    }

    public List<DestinationExportDTO> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<DestinationExportDTO> destinations) {
        this.destinations = destinations;
    }
}