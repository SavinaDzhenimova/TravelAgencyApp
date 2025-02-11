package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Result;
import org.travelagency.model.importDTO.AddExcursionDTO;

public interface ExcursionService {

    Result addExcursion(AddExcursionDTO addExcursionDTO);
}
