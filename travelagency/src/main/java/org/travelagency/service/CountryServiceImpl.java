package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Country;
import org.travelagency.model.enums.ContinentName;
import org.travelagency.model.exportDTO.CountryMenuDTO;
import org.travelagency.model.exportDTO.CountryMenuInfo;
import org.travelagency.repository.CountryRepository;
import org.travelagency.service.interfaces.CountryService;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CountryMenuInfo getAllCountries() {

        List<CountryMenuDTO> europeCountries = this.getAllCountriesByContinentName(ContinentName.EUROPE);

        List<CountryMenuDTO> africaCountries = this.getAllCountriesByContinentName(ContinentName.AFRICA);

        List<CountryMenuDTO> asiaCountries = this.getAllCountriesByContinentName(ContinentName.ASIA);

        List<CountryMenuDTO> southAmericaCountries = this.getAllCountriesByContinentName(ContinentName.SOUTH_AMERICA);

        List<CountryMenuDTO> northAmericaCountries = this.getAllCountriesByContinentName(ContinentName.NORTH_AMERICA);

        List<CountryMenuDTO> australiaCountries = this.getAllCountriesByContinentName(ContinentName.AUSTRALIA);

        return new CountryMenuInfo(europeCountries, africaCountries, asiaCountries,
                southAmericaCountries, northAmericaCountries, australiaCountries);
    }

    @Override
    public List<CountryMenuDTO> getAllCountriesByContinentName(ContinentName continentName) {
        List<Country> countries = this.countryRepository.findAll();

        List<CountryMenuDTO> countryMenuDTO = countries.stream()
                .map(country -> {
                    CountryMenuDTO dto = this.modelMapper.map(country, CountryMenuDTO.class);
                    dto.setId(country.getDestination().getId());
                    dto.setName(country.getDestination().getName());
                    dto.setContinentName(country.getContinent().getContinentName());

                    return dto;
                })
                .filter(dto -> dto.getContinentName().equals(continentName))
                .toList();

        return countryMenuDTO;
    }

    @Override
    public void saveAndFlushCountry(Country country) {
        this.countryRepository.saveAndFlush(country);
    }
}