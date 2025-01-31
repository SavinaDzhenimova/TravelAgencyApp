package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Destination;
import org.travelagency.model.exportDTO.CountryViewDTO;
import org.travelagency.model.exportDTO.DestinationViewDTO;
import org.travelagency.model.exportDTO.DestinationViewInfo;
import org.travelagency.model.exportDTO.EmbassyViewDTO;
import org.travelagency.repository.DestinationRepository;
import org.travelagency.service.interfaces.DestinationService;

import java.util.Optional;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final ModelMapper modelMapper;

    public DestinationServiceImpl(DestinationRepository destinationRepository, ModelMapper modelMapper) {
        this.destinationRepository = destinationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DestinationViewDTO getDestinationByCountryName(String countryName) {

        Destination destination = this.getDestinationByName(countryName);

        if (destination == null) {
            throw new NullPointerException("Not existing destination");
        }

        DestinationViewDTO destinationViewDTO = this.modelMapper.map(destination, DestinationViewDTO.class);

        destinationViewDTO.setId(destination.getId());
        destinationViewDTO.setName(destination.getName());
        destinationViewDTO.setDescription(destination.getDescription());
        destinationViewDTO.setImageUrl(destination.getImageUrl());
        destinationViewDTO.setVisaRequirements(destination.getVisaRequirements());
        destinationViewDTO.setGoodToKnow(destination.getGoodToKnow());
        destinationViewDTO.setTimeToVisit(destination.getTimeToVisit());

        return destinationViewDTO;
    }

    @Override
    public CountryViewDTO getCountryByDestination(String countryName) {
        Destination destination = this.getDestinationByName(countryName);

        if (destination == null) {
            throw new NullPointerException("Not existing destination");
        }

        CountryViewDTO countryViewDTO = this.modelMapper.map(destination, CountryViewDTO.class);

        countryViewDTO.setCapital(destination.getCountry().getCapital());
        countryViewDTO.setCurrency(destination.getCountry().getCurrency());
        countryViewDTO.setTimeDifference(destination.getCountry().getTimeDifference());

        return countryViewDTO;
    }

    @Override
    public EmbassyViewDTO getEmbassyByDestination(String countryName) {
        Destination destination = this.getDestinationByName(countryName);

        if (destination == null) {
            throw new NullPointerException("Not existing destination");
        }

        EmbassyViewDTO embassyViewDTO = this.modelMapper.map(destination, EmbassyViewDTO.class);

        embassyViewDTO.setAddress(destination.getCountry().getEmbassy().getAddress());
        embassyViewDTO.setPhoneNumber(destination.getCountry().getEmbassy().getPhoneNumber());
        embassyViewDTO.setFax(destination.getCountry().getEmbassy().getFax());
        embassyViewDTO.setDutyPhone(destination.getCountry().getEmbassy().getDutyPhone());
        embassyViewDTO.setEmail(destination.getCountry().getEmbassy().getEmail());
        embassyViewDTO.setWebpage(destination.getCountry().getEmbassy().getWebpage());

        return embassyViewDTO;
    }

    @Override
    public DestinationViewInfo getDestinationInfo(String countryName) {
        DestinationViewDTO destination = this.getDestinationByCountryName(countryName);

        CountryViewDTO country = this.getCountryByDestination(countryName);

        EmbassyViewDTO embassy = this.getEmbassyByDestination(countryName);

        return new DestinationViewInfo(destination, country, embassy);
    }

    private Destination getDestinationByName(String countryName) {
        Optional<Destination> optionalDestination = this.destinationRepository.findByName(countryName);

        if (optionalDestination.isEmpty()) {
            return null;
        }

        return optionalDestination.get();
    }
}