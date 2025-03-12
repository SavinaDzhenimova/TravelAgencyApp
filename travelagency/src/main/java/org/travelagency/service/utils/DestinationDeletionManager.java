package org.travelagency.service.utils;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.*;
import org.travelagency.service.interfaces.CountryService;
import org.travelagency.service.interfaces.DestinationService;
import org.travelagency.service.interfaces.EmbassyService;
import org.travelagency.service.interfaces.ExcursionService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class DestinationDeletionManager {

    private final DestinationService destinationService;
    private final CountryService countryService;
    private final EmbassyService embassyService;
    private final ExcursionService excursionService;
    private final ExcursionDeletionManager excursionDeletionManager;

    public DestinationDeletionManager(DestinationService destinationService, CountryService countryService,
                                      EmbassyService embassyService, ExcursionService excursionService,
                                      ExcursionDeletionManager excursionDeletionManager) {
        this.destinationService = destinationService;
        this.countryService = countryService;
        this.embassyService = embassyService;
        this.excursionService = excursionService;
        this.excursionDeletionManager = excursionDeletionManager;
    }

    @Transactional
    public Result deleteDestination(String destinationName) {

        Optional<Destination> optDestination = this.destinationService.findDestinationByDestinationName(destinationName);

        if (optDestination.isEmpty()) {
            return new Result(false, "Дестинацията, което се опитвате да изтриете, не съществува!");
        }

        Long destinationId = optDestination.get().getId();

        List<Excursion> excursions = this.excursionService.findAllExcursionsByDestinationId(destinationId);

        excursions.forEach(excursion -> {
            try {
                this.excursionDeletionManager.deleteExcursion(excursion.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.destinationService.deleteDestinationByDestinationName(destinationName);
        this.embassyService.deleteEmbassyByCountryName(destinationName);
        this.countryService.deleteCountryByName(destinationName);

        List<Excursion> excursionsAfterDeletion = this.excursionService.findAllExcursionsByDestinationId(destinationId);
        Optional<Destination> optionalDestination = this.destinationService.findDestinationByDestinationName(destinationName);
        Optional<Embassy> optionalEmbassy = this.embassyService.findEmbassyByName(destinationName);
        Optional<Country> optionalCountry = this.countryService.findCountryByName(destinationName);

        if (!excursionsAfterDeletion.isEmpty() || optionalDestination.isPresent()
                || optionalEmbassy.isPresent() || optionalCountry.isPresent()) {
            return new Result(false, "Нещо се обърка! Дестинация " + destinationName + " не можа да бъде изтрита!");
        }

        return new Result(true, "Успешно изтрихте дестинация " + destinationName);
    }
}
