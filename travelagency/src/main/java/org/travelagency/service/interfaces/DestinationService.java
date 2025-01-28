package org.travelagency.service.interfaces;

import org.travelagency.model.exportDTO.DestinationViewDTO;

import java.util.List;

public interface DestinationService {

    DestinationViewDTO getDestinationByCountryName(String countryName);
}