package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.*;
import org.travelagency.model.exportDTO.*;
import org.travelagency.model.importDTO.AddDestinationDTO;
import org.travelagency.repository.DestinationRepository;
import org.travelagency.service.interfaces.ContinentService;
import org.travelagency.service.interfaces.CountryService;
import org.travelagency.service.interfaces.DestinationService;
import org.travelagency.service.interfaces.EmbassyService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final ContinentService continentService;
    private final CountryService countryService;
    private final EmbassyService embassyService;
    private final ModelMapper modelMapper;

    public DestinationServiceImpl(DestinationRepository destinationRepository, ContinentService continentService,
                                  CountryService countryService, EmbassyService embassyService, ModelMapper modelMapper) {
        this.destinationRepository = destinationRepository;
        this.continentService = continentService;
        this.countryService = countryService;
        this.embassyService = embassyService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Result addDestination(AddDestinationDTO addDestinationDTO) {
        if (addDestinationDTO == null) {
            return new Result(false, "Дестинацията не съществува!");
        }

        Optional<Destination> optionalDestination = this.destinationRepository.findByName(addDestinationDTO.getCountryName());
        if (optionalDestination.isPresent()) {
            return new Result(false, "Дестинация с това име вече съществува!");
        }

        Optional<Country> optionalCountry = this.countryService.findCountryByName(addDestinationDTO.getCountryName());
        if (optionalCountry.isPresent()) {
            return new Result(false, "Държава с това име вече съществува!");
        }

        Optional<Embassy> optionalEmbassy = this.embassyService.findEmbassyByName(addDestinationDTO.getCountryName());
        if (optionalEmbassy.isPresent()) {
            return new Result(false, "Посолство с това име вече съществува!");
        }

        Optional<Continent> optionalContinent = this.continentService.findContinentByName(addDestinationDTO.getContinentName());
        if (optionalContinent.isEmpty()) {
            return new Result(false, "Континент с това име не съществува!");
        }

        Country country = new Country();
        country.setName(addDestinationDTO.getCountryName());
        country.setCapital(addDestinationDTO.getCapital());
        country.setCurrency(addDestinationDTO.getCurrency());
        country.setTimeDifference(addDestinationDTO.getTimeDifference());
        country.setContinent(optionalContinent.get());

        this.countryService.saveAndFlushCountry(country);

        Embassy embassy = new Embassy();
        embassy.setName(addDestinationDTO.getCountryName());
        embassy.setAddress(addDestinationDTO.getAddress());
        embassy.setPhoneNumber(addDestinationDTO.getPhoneNumber());
        embassy.setFax(addDestinationDTO.getFax());
        embassy.setDutyPhone(addDestinationDTO.getDutyPhone());
        embassy.setEmail(addDestinationDTO.getEmail());
        embassy.setWebpage(addDestinationDTO.getWebpage());
        embassy.setCountry(country);

        this.embassyService.saveAndFlushEmbassy(embassy);

        country.setEmbassy(embassy);
        this.countryService.saveAndFlushCountry(country);

        Destination destination = new Destination();
        destination.setName(addDestinationDTO.getCountryName());
        destination.setImageUrl(addDestinationDTO.getImageUrl());
        destination.setDescription(addDestinationDTO.getDescription());
        destination.setVisaRequirements(addDestinationDTO.getVisaRequirements());
        destination.setTimeToVisit(addDestinationDTO.getTimeToVisit());
        destination.setGoodToKnow(addDestinationDTO.getGoodToKnow());
        destination.setCountry(country);

        this.destinationRepository.saveAndFlush(destination);

        return new Result(true, "Новата дестинация беше успешно създадена! " +
                "Можете да я намерите на страницата с всички дестинации!");
    }

    @Override
    public DestinationsExportListDTO getDestinationsForIndexPage() {
        List<Destination> destinations = this.destinationRepository.findAll();

        List<DestinationExportDTO> destinationExportList = destinations.stream()
                .sorted(Comparator.comparing(Destination::getId).reversed())
                .map(destination -> {
                    DestinationExportDTO dto = this.modelMapper.map(destination, DestinationExportDTO.class);

                    dto.setName(destination.getName());
                    dto.setCapital(destination.getCountry().getCapital());
                    dto.setContinentName(destination.getCountry().getContinent().getName());
                    dto.setImageUrl(destination.getImageUrl());
                    dto.setTimeDifference(destination.getCountry().getTimeDifference());

                    return dto;
                })
                .limit(3)
                .toList();

        return new DestinationsExportListDTO(destinationExportList);
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

    @Override
    public DestinationMenuInfo getAllDestinations() {
        List<Destination> destinations = this.destinationRepository.findAll();

        List<DestinationMenuDTO> destinationMenuList = destinations.stream()
                .map(destination -> {
                    DestinationMenuDTO dto = this.modelMapper.map(destination, DestinationMenuDTO.class);

                    dto.setId(destination.getId());
                    dto.setName(destination.getName());

                    return dto;
                })
                .sorted(Comparator.comparing(DestinationMenuDTO::getName))
                .toList();

        return new DestinationMenuInfo(destinationMenuList);
    }

    private Destination getDestinationByName(String countryName) {
        Optional<Destination> optionalDestination = this.destinationRepository.findByName(countryName);

        if (optionalDestination.isEmpty()) {
            return null;
        }

        return optionalDestination.get();
    }
}