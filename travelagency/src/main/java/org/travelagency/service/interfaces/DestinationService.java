package org.travelagency.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.travelagency.model.entity.Destination;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.country.CountryViewDTO;
import org.travelagency.model.exportDTO.destination.DestinationMenuInfo;
import org.travelagency.model.exportDTO.destination.DestinationViewDTO;
import org.travelagency.model.exportDTO.destination.DestinationViewInfo;
import org.travelagency.model.exportDTO.destination.DestinationsExportListDTO;
import org.travelagency.model.exportDTO.embassy.EmbassyViewDTO;
import org.travelagency.model.importDTO.AddDestinationDTO;

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