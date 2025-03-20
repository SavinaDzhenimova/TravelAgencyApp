package org.travelagency.service.utils;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.*;
import org.travelagency.service.interfaces.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    public Result deleteExcursionReservationsByDate(Long id, LocalDate dateToDelete) {
        Optional<Excursion> optionalExcursion = this.excursionService.findExcursionById(id);

        if (optionalExcursion.isEmpty()) {
            return new Result(false, "Екскурзията, която се опитвате да редактирате, не съществува!");
        }

        Excursion excursion = optionalExcursion.get();

        this.reservationService.deleteAllReservationsByExcursionDate(dateToDelete);

        List<Reservation> deletedReservations = this.reservationService.findAllReservationsByExcursionDate(dateToDelete);

        if (!deletedReservations.isEmpty()) {
            return new Result(false, "Резервациите за тази дата на екскурзията не можаха да бъдат изтрити!");
        }

        if (excursion.getDates().size() == 1) {
            return new Result(false, "Тази дата е единствена за екскурзията и не можете да я изтриете!");
        }

        if (!excursion.getDates().contains(dateToDelete) ) {
            return new Result(false, "Датата не съществува за тази екскурзия!");
        }

        excursion.getDates().remove(dateToDelete);
        this.excursionService.saveAndFlushExcursion(excursion);

        return new Result(true, "Датата за тази екскурзия беше успешно изтрита!");
    }

    public Result deleteExcursionDayById(Long dayId, Long excursionId) {

        Optional<Day> optionalDay = this.dayService.findDayById(dayId);
        Optional<Excursion> optionalExcursion = this.excursionService.findExcursionById(excursionId);

        if (optionalDay.isEmpty()) {
            return new Result(false, "Денят от тази екскурзия, който се опитвате да изтриете, не съществува!");
        }

        if (optionalExcursion.isEmpty()) {
            return new Result(false, "Екскурзията, която се опитвате да редактирате, не съществува!");
        }

        Excursion excursion = optionalExcursion.get();
        Program program = excursion.getProgram();

        if (program.getDays().size() == 1) {
            return new Result(false, "Този ден е единствен от програмата на екскурзията и не можете да го изтриете!");
        }

        this.dayService.deleteDayById(dayId);

        Optional<Day> optionalDayAfterDeletion = this.dayService.findDayById(dayId);

        if (optionalDayAfterDeletion.isPresent()) {
            return new Result(false, "Денят от програмата за тази екскурзия не беше изтрит!");
        }

        program.setEndurance(program.getEndurance() - 1);
        this.programService.saveAndFlushProgram(program);

        return new Result(true, "Ден от програмата за тази екскурзия беше успешно изтрит!");
    }
}
