package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Destination;
import org.travelagency.model.enums.ContinentName;
import org.travelagency.model.exportDTO.DestinationViewDTO;
import org.travelagency.repository.DestinationRepository;
import org.travelagency.service.interfaces.DestinationService;

import java.util.List;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final ModelMapper modelMapper;

    public DestinationServiceImpl(DestinationRepository destinationRepository, ModelMapper modelMapper) {
        this.destinationRepository = destinationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DestinationViewDTO> getAllDestinationsByContinent(ContinentName continentName) {
        List<Destination> destinations = this.destinationRepository.findAll();

        return destinations.stream()
                .map(destination -> {
                    DestinationViewDTO dto = this.modelMapper.map(destination, DestinationViewDTO.class);
                    dto.setCapital(destination.getCountry().getCapital());
                    dto.setCurrency(destination.getCountry().getCurrency());
                    dto.setTimeDifference(destination.getCountry().getTimeDifference());
                    dto.setContinentName(destination.getCountry().getContinent().getContinentName());
                    dto.setContinent(destination.getCountry().getContinent().getName());

                    return dto;
                })
                .filter(destinationViewDTO -> destinationViewDTO.getContinentName().equals(continentName))
                .toList();
    }
}
