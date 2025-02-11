package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Excursion;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.ExcursionExportDTO;
import org.travelagency.model.exportDTO.ExcursionViewInfo;
import org.travelagency.model.importDTO.AddExcursionDTO;

import java.util.Optional;

public interface ExcursionService {

    Result addExcursion(AddExcursionDTO addExcursionDTO);

    ExcursionViewInfo getAllExcursions();

    ExcursionViewInfo getExcursionsByDestinationName(String destinationName);

    ExcursionExportDTO getExcursionByName(String excursionName);

    Optional<Excursion> findExcursionByExcursionName(String excursionName);

    void saveAndFlushExcursion(Excursion excursion);
}
