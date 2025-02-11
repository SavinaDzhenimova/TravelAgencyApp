package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Destination;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.*;
import org.travelagency.model.importDTO.AddDestinationDTO;

import java.util.List;
import java.util.Optional;

public interface DestinationService {

    DestinationViewDTO getDestinationByCountryName(String countryName);

    CountryViewDTO getCountryByDestination(String countryName);

    EmbassyViewDTO getEmbassyByDestination(String countryName);

    DestinationViewInfo getDestinationInfo(String countryName);

    Result addDestination(AddDestinationDTO addDestinationDTO);

    DestinationsExportListDTO getDestinationsForIndexPage();

    DestinationMenuInfo getAllDestinations();

    Optional<Destination> findDestinationByDestinationName(String destinationName);
}