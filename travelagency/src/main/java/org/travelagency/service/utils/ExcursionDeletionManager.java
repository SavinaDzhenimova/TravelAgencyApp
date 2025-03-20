package org.travelagency.service.utils;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.*;
import org.travelagency.model.importDTO.UpdateExcursionDTO;
import org.travelagency.service.interfaces.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExcursionDeletionManager {

    private final ExcursionService excursionService;
    private final ImageService imageService;
    private final ReservationService reservationService;
    private final DayService dayService;
    private final ProgramService programService;
    private final DestinationService destinationService;
    private final EmployeeService employeeService;

    public ExcursionDeletionManager(ExcursionService excursionService, ImageService imageService,
                                    ReservationService reservationService, DayService dayService,
                                    ProgramService programService, DestinationService destinationService,
                                    EmployeeService employeeService) {
        this.excursionService = excursionService;
        this.imageService = imageService;
        this.reservationService = reservationService;
        this.dayService = dayService;
        this.programService = programService;
        this.destinationService = destinationService;
        this.employeeService = employeeService;
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

        List<Reservation> reservationsForDate = this.reservationService.findAllReservationsByExcursionDate(dateToDelete);

        if (!reservationsForDate.isEmpty()) {
            return new Result(false, "Тази дата не може да бъде изтрита понеже за нея има направени резервации!");
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

    @Transactional
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

        program.setEndurance(program.getEndurance() - 1);
        this.programService.saveAndFlushProgram(program);

        return new Result(true, "Ден от програмата за тази екскурзия беше успешно изтрит!");
    }

    public Result updateExcursion(UpdateExcursionDTO updateExcursionDTO, String decodedExcursionName) {

        Optional<Excursion> optionalExcursion = this.excursionService.findExcursionByExcursionName(decodedExcursionName);

        if (optionalExcursion.isEmpty()) {
            return new Result(false, "Екскурзията, която се опитвате да редактирате, не съществува!");
        }

        Excursion excursion = optionalExcursion.get();

        if (!excursion.getName().equals(updateExcursionDTO.getExcursionName())) {
            excursion.setName(updateExcursionDTO.getExcursionName());
        }

        if (!excursion.getPrice().equals(updateExcursionDTO.getPrice())) {
            excursion.setPrice(updateExcursionDTO.getPrice());
        }

        if (!excursion.getTransportType().equals(updateExcursionDTO.getTransport())) {
            excursion.setTransportType(updateExcursionDTO.getTransport());
        }

        if (!excursion.getDestination().getName().equals(updateExcursionDTO.getDestination())) {
            String destinationName = updateExcursionDTO.getDestination();

            Optional<Destination> optionalDestination = this.destinationService.findDestinationByDestinationName(destinationName);

            if (optionalDestination.isEmpty()) {
                return new Result(false,
                        "Дестинацията, която се опитвате да назначите на тази екскурзия, не съществува!");
            }

            excursion.setDestination(optionalDestination.get());
        }

        if (!excursion.getGuide().getId().equals(updateExcursionDTO.getGuideId())) {
            Long guideId = updateExcursionDTO.getGuideId();

            Optional<Employee> optionalEmployee = this.employeeService.findEmployeeById(guideId);

            if (optionalEmployee.isEmpty()) {
                return new Result(false,
                        "Ръководителят, който се опитвате да назначите на тази екскурзия, не съществува!");
            }

            excursion.setGuide(optionalEmployee.get());
        }

        boolean areUpdated = this.updateDates(updateExcursionDTO, excursion);
        if (!areUpdated) {
            return new Result(false,
                    "Датите не бяха актуализирани понеже за някои от тях вече има направени резервации!");
        }

        Program program = excursion.getProgram();
        this.updateProgram(program, updateExcursionDTO);

        excursion.setProgram(program);
        this.excursionService.saveAndFlushExcursion(excursion);

        return new Result(true, "Успешно редактирахте избраната от вас екскурзия!");
    }

    private void updateProgram(Program program, UpdateExcursionDTO updateExcursionDTO) {

        this.dayService.deleteAllDaysByProgramId(program.getId());

        List<Day> updatedDays = updateExcursionDTO.getDays().stream()
                .map(dayExportDTO -> {
                    Day day = new Day();

                    day.setDescription(dayExportDTO.getDescription());
                    day.setProgram(program);

                    this.dayService.saveAndFlushDay(day);

                    return day;
                }).toList();

        program.setDays(updatedDays);
        this.programService.saveAndFlushProgram(program);
    }

    public boolean updateDates(UpdateExcursionDTO updateExcursionDTO, Excursion excursion) {
        Set<LocalDate> newLocalDates = updateExcursionDTO.getDates().stream()
                .map(date -> LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .filter(date -> LocalDate.now().isBefore(date))
                .collect(Collectors.toSet());

        Set<LocalDate> datesToAdd = new HashSet<>(newLocalDates);
        datesToAdd.removeAll(excursion.getDates());

        Set<LocalDate> datesToRemove = new HashSet<>(excursion.getDates());
        datesToRemove.removeAll(newLocalDates);

        for (LocalDate dateToRemove : datesToRemove) {
            List<Reservation> reservationsForDate = this.reservationService.findAllReservationsByExcursionDate(dateToRemove);

            if (!reservationsForDate.isEmpty()) {
                return false;
            }
        }

        excursion.getDates().removeAll(datesToRemove);
        excursion.getDates().addAll(datesToAdd);
        return true;
    }
}
