package org.travelagency.service.interfaces;

import org.travelagency.model.enums.ContinentName;
import org.travelagency.model.exportDTO.DestinationViewDTO;

import java.util.List;

public interface DestinationService {
    List<DestinationViewDTO> getAllDestinationsByContinent(ContinentName continentName);
}
