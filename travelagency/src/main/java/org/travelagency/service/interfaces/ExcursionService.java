package org.travelagency.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.travelagency.model.entity.Excursion;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.excursion.ExcursionExportDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionViewDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionViewInfo;
import org.travelagency.model.importDTO.AddExcursionDTO;
import org.travelagency.model.importDTO.AddInquiryDTO;

import java.util.List;
import java.util.Optional;

public interface ExcursionService {

    Result addExcursion(AddExcursionDTO addExcursionDTO);

    void sendInquiryEmail(AddInquiryDTO addInquiryDTO, String excursionName);

    ExcursionViewInfo getExcursionsForIndexPage();

    Page<ExcursionViewDTO> getAllExcursions(Pageable pageable);

    ExcursionViewInfo getAllExcursionsByDestinationName(String destinationName);

    ExcursionExportDTO getExcursionDetailsByName(String excursionName);

    Optional<Excursion> findExcursionByExcursionName(String excursionName);

    void saveAndFlushExcursion(Excursion excursion);

    List<String> getAllExcursionsNames();
}
