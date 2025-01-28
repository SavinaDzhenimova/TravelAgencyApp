package org.travelagency.service.interfaces;

import org.travelagency.model.exportDTO.CountryViewDTO;
import org.travelagency.model.exportDTO.DestinationViewDTO;
import org.travelagency.model.exportDTO.DestinationViewInfo;
import org.travelagency.model.exportDTO.EmbassyViewDTO;

import java.util.List;

public interface DestinationService {

    DestinationViewDTO getDestinationByCountryName(String countryName);

    CountryViewDTO getCountryByDestination(String countryName);

    EmbassyViewDTO getEmbassyByDestination(String countryName);

    DestinationViewInfo getDestinationInfo(String countryName);
}