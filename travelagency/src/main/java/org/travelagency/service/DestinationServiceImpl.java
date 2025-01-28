package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Destination;
import org.travelagency.model.exportDTO.DestinationViewDTO;
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
        Optional<Destination> optionalDestination = this.destinationRepository.findByName(countryName);

        if (optionalDestination.isEmpty()) {
            return null;
        }

        Destination destination = optionalDestination.get();

        DestinationViewDTO dto = this.modelMapper.map(destination, DestinationViewDTO.class);

        dto.setId(destination.getId());
        dto.setName(destination.getName());
        dto.setDescription(destination.getDescription());
        dto.setImageUrl(destination.getImageUrl());
        dto.setVisaRequirements(destination.getVisaRequirements());
        dto.setGoodToKnow(destination.getGoodToKnow());
        dto.setTimeToVisit(destination.getTimeToVisit());
        dto.setCapital(destination.getCountry().getCapital());
        dto.setCurrency(destination.getCountry().getCurrency());
        dto.setTimeDifference(destination.getCountry().getTimeDifference());

        return dto;
    }
}