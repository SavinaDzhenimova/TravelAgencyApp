package org.travelagency.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.travelagency.model.entity.Excursion;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.excursion.ExcursionExportDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionViewDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionViewInfo;
import org.travelagency.model.importDTO.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExcursionService {

    UpdateExcursionDTO getExcursionDetailsForUpdate(String excursionName);

    Result addExcursion(AddExcursionDTO addExcursionDTO);

    void sendInquiryEmail(AddInquiryDTO addInquiryDTO, String excursionName);

    ExcursionViewInfo getExcursionsForIndexPage();

    Page<ExcursionViewDTO> getAllExcursions(Pageable pageable);

    Page<ExcursionViewDTO> getAllExcursionsForLoggedEmployee(Pageable pageable);

    Page<ExcursionViewDTO> getAllExcursionsByDestinationName(String destinationName, Pageable pageable);

    ExcursionExportDTO getExcursionDetailsByName(String excursionName);

    void deleteExcursionById(Long id);

    Optional<Excursion> findExcursionByExcursionName(String excursionName);

    Optional<Excursion> findExcursionById(Long id);

    void saveAndFlushExcursion(Excursion excursion);

    Page<String> getAllExcursionsNames(Pageable pageable);

    Result updateExcursion(UpdateExcursionDTO updateExcursionDTO, String decodedExcursionName);

    List<Excursion> findAllExcursionsByDestinationId(Long destinationId);

    Result updateExcursionDates(UpdateExcursionDatesDTO updateExcursionDatesDTO, String decodedExcursionName);

    Result updateExcursionProgram(UpdateExcursionProgramDTO updateExcursionProgramDTO, String decodedExcursionName);
}