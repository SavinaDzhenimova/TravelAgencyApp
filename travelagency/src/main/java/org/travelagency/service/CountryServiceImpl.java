package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Country;
import org.travelagency.model.exportDTO.CountryMenuDTO;
import org.travelagency.model.exportDTO.CountryMenuInfo;
import org.travelagency.repository.CountryRepository;
import org.travelagency.service.interfaces.CountryService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

        List<CountryMenuDTO> europeCountries = this.getAllCountriesByContinentName("Европа");

        List<CountryMenuDTO> africaCountries = this.getAllCountriesByContinentName("Африка");

        List<CountryMenuDTO> asiaCountries = this.getAllCountriesByContinentName("Азия");

        List<CountryMenuDTO> southAmericaCountries = this.getAllCountriesByContinentName("Северна Америка");

        List<CountryMenuDTO> northAmericaCountries = this.getAllCountriesByContinentName("Южна Америка");

        List<CountryMenuDTO> australiaCountries = this.getAllCountriesByContinentName("Австралия");

        return new CountryMenuInfo(europeCountries, africaCountries, asiaCountries,
                southAmericaCountries, northAmericaCountries, australiaCountries);
    }

    @Override
    public List<CountryMenuDTO> getAllCountriesByContinentName(String continentName) {
        List<Country> countries = this.countryRepository.findAll();

        List<CountryMenuDTO> countryMenuDTO = countries.stream()
                .map(country -> {
                    CountryMenuDTO dto = this.modelMapper.map(country, CountryMenuDTO.class);

                    dto.setId(country.getId());
                    dto.setName(country.getName());
                    dto.setContinentName(country.getContinent().getName());

                    return dto;
                })
                .filter(dto -> dto.getContinentName().equals(continentName))
                .sorted(Comparator.comparing(CountryMenuDTO::getName))
                .toList();

        return countryMenuDTO;
    }

    @Override
    public void saveAndFlushCountry(Country country) {
        this.countryRepository.saveAndFlush(country);
    }

    @Override
    public Optional<Country> findCountryByName(String name) {
        return this.countryRepository.findByName(name);
    }
}