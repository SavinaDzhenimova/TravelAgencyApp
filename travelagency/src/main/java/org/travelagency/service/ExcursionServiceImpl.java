package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.model.entity.*;
import org.travelagency.model.enums.TransportType;
import org.travelagency.model.importDTO.AddExcursionDTO;
import org.travelagency.repository.ExcursionRepository;
import org.travelagency.service.interfaces.DayService;
import org.travelagency.service.interfaces.DestinationService;
import org.travelagency.service.interfaces.ExcursionService;
import org.travelagency.service.interfaces.ProgramService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ExcursionServiceImpl implements ExcursionService {

    private final ExcursionRepository excursionRepository;
    private final ProgramService programService;
    private final DayService dayService;
    private final DestinationService destinationService;

    public ExcursionServiceImpl(ExcursionRepository excursionRepository, ProgramService programService,
                                DayService dayService, DestinationService destinationService) {
        this.excursionRepository = excursionRepository;
        this.programService = programService;
        this.dayService = dayService;
        this.destinationService = destinationService;
    }

    @Override
    public Result addExcursion(AddExcursionDTO addExcursionDTO) {
        if (addExcursionDTO == null) {
            return new Result(false, "Екскурзията не съществува!");
        }

        boolean isTransportTypeValid = this.isTransportTypeValid(addExcursionDTO.getTransportType());

        if (!isTransportTypeValid) {
            return new Result(false, "Избраният тип транспорт не е валиден!");
        }

        Optional<Destination> optionalDestination = this.destinationService
                .findDestinationByDestinationName(addExcursionDTO.getDestination());

        if (optionalDestination.isEmpty()) {
            return new Result(false, "Избраната дестинация не съществува!");
        }

        Program program = this.mapDtoToProgram(addExcursionDTO);
        Excursion excursion = this.mapDtoToExcursion(addExcursionDTO, program, optionalDestination.get());
        List<Day> dayList = this.mapDtoToDay(addExcursionDTO.getDays(), program);

        program.setDays(dayList);
        program.setExcursion(excursion);
        this.programService.saveAndFlushProgram(program);

        return new Result(true, "Успешно добавихте нова екскурзия! " +
                "Може да я видите на страницата с всички екскурзии!");
    }

    private Program mapDtoToProgram(AddExcursionDTO addExcursionDTO) {
        Program program = new Program();

        program.setEndurance(addExcursionDTO.getEndurance());
        program.setDays(new ArrayList<>());

        this.programService.saveAndFlushProgram(program);

        return program;
    }

    private Excursion mapDtoToExcursion(AddExcursionDTO addExcursionDTO, Program program, Destination destination) {
        Excursion excursion = new Excursion();

        excursion.setName(addExcursionDTO.getName());
        excursion.setDate(addExcursionDTO.getDate());
        excursion.setPrice(addExcursionDTO.getPrice());
        excursion.setTransportType(addExcursionDTO.getTransportType());
        excursion.setDestination(destination);
        excursion.setProgram(program);
        excursion.setImageUrl(new ArrayList<>());

        this.excursionRepository.saveAndFlush(excursion);

        return excursion;
    }

    private List<Day> mapDtoToDay(List<String> days, Program program) {
        List<Day> daysList = new ArrayList<>();

        for (int i = 0; i < days.size(); i++) {
            Day day = new Day();

            day.setDayNumber(i + 1);
            day.setDescription(days.get(i));
            day.setProgram(program);

            this.dayService.saveAndFlushDay(day);
            daysList.add(day);
        }

        return daysList;
    }

    private boolean isTransportTypeValid(TransportType transportType) {
        return Arrays.asList(TransportType.values()).contains(transportType);
    }
}
