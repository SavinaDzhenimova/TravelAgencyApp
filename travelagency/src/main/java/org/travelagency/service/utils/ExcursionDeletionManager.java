package org.travelagency.service.utils;

import org.springframework.stereotype.Component;
import org.travelagency.model.entity.*;
import org.travelagency.service.interfaces.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class ExcursionDeletionManager {

    private final ExcursionService excursionService;
    private final ImageService imageService;
    private final ReservationService reservationService;
    private final DayService dayService;
    private final ProgramService programService;

    public ExcursionDeletionManager(ExcursionService excursionService, ImageService imageService,
                                    ReservationService reservationService, DayService dayService, ProgramService programService) {
        this.excursionService = excursionService;
        this.imageService = imageService;
        this.reservationService = reservationService;
        this.dayService = dayService;
        this.programService = programService;
    }

    public Result deleteExcursion(Long excursionId) throws IOException {

        Optional<Excursion> optionalExcursion = this.excursionService.findExcursionById(excursionId);

        if (optionalExcursion.isEmpty()) {
            return new Result(false, "Тази екскурзия не съществува!");
        }

        Excursion excursion = optionalExcursion.get();
        String excursionName = excursion.getName();
        Long programId = excursion.getProgram().getId();

        this.imageService.deleteAllImagesByExcursionId(excursionId);
        this.imageService.deleteImagesFromDirectory(excursion.getImages());

        this.reservationService.deleteAllReservationsByExcursionId(excursionId);

        this.excursionService.deleteExcursionById(excursionId);

        List<Image> imagesAfterDeletion = this.imageService.findAllImagesByExcursionId(excursionId);
        List<Reservation> reservationsAfterDeletion = this.reservationService.findAllReservationsByExcursionId(excursionId);
        Optional<Excursion> optionalExcursionAfterDeletion = this.excursionService.findExcursionById(excursionId);

        this.dayService.deleteAllDaysByProgramId(programId);
        this.programService.deleteProgramById(programId);

        List<Day> daysAfterDeletion = this.dayService.findAllDaysByProgramId(programId);
        Optional<Program> optionalProgramAfterDeletion = this.programService.findProgramById(programId);

        if (!imagesAfterDeletion.isEmpty() || !reservationsAfterDeletion.isEmpty() || !daysAfterDeletion.isEmpty()
                || optionalExcursionAfterDeletion.isPresent() || optionalProgramAfterDeletion.isPresent()) {
            return new Result(false, "Нещо се обърка! Екскурзията не можа да бъде изтрита!");
        }

        return new Result(true, "Успешно изтрихте екскурзия " + excursionName + "!");
    }
}
