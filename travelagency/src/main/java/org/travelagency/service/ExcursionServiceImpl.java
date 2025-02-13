package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.model.entity.*;
import org.travelagency.model.enums.TransportType;
import org.travelagency.model.exportDTO.day.DayExportDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionExportDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionViewDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionViewInfo;
import org.travelagency.model.importDTO.AddExcursionDTO;
import org.travelagency.repository.ExcursionRepository;
import org.travelagency.service.interfaces.*;

import java.io.IOException;
import java.util.*;

@Service
public class ExcursionServiceImpl implements ExcursionService {

    private final ExcursionRepository excursionRepository;
    private final ProgramService programService;
    private final DayService dayService;
    private final DestinationService destinationService;
    private final ImageService imageService;

    public ExcursionServiceImpl(ExcursionRepository excursionRepository, ProgramService programService,
                                DayService dayService, DestinationService destinationService, ImageService imageService) {
        this.excursionRepository = excursionRepository;
        this.programService = programService;
        this.dayService = dayService;
        this.destinationService = destinationService;
        this.imageService = imageService;
    }

    @Override
    public ExcursionViewInfo getAllExcursions() {
        List<Excursion> excursions = this.excursionRepository.findAll();

        if (excursions.isEmpty()) {
            return null;
        }

        List<Excursion> sortedExcursionsByDate = excursions.stream()
                .sorted(Comparator.comparing(Excursion::getDate))
                .toList();

        List<ExcursionViewDTO> excursionViewDTOList = this.mapExcursionsToExcursionViewDTOList(sortedExcursionsByDate);

        return new ExcursionViewInfo(excursionViewDTOList);
    }

    @Override
    public ExcursionViewInfo getAllExcursionsByDestinationName(String destinationName) {
        Optional<Destination> optionalDestination = this.destinationService.findDestinationByDestinationName(destinationName);

        if (optionalDestination.isEmpty()) {
            return null;
        }

        Destination destination = optionalDestination.get();

        List<Excursion> excursions = destination.getExcursions().stream()
                .sorted(Comparator.comparing(Excursion::getDate))
                .toList();

        if (excursions.isEmpty()) {
            return null;
        }

        List<ExcursionViewDTO> excursionViewDTOList = this.mapExcursionsToExcursionViewDTOList(excursions);

        return new ExcursionViewInfo(excursionViewDTOList);
    }

    private List<ExcursionViewDTO> mapExcursionsToExcursionViewDTOList(List<Excursion> excursions) {
        return excursions.stream()
                .map(excursion -> {
                    ExcursionViewDTO dto = new ExcursionViewDTO();

                    dto.setName(excursion.getName());
                    dto.setPrice(excursion.getPrice());
                    dto.setDate(excursion.getDate());
                    dto.setDestination(excursion.getDestination().getName());
                    dto.setEndurance(excursion.getProgram().getEndurance());

                    if (excursion.getImages().isEmpty()) {
                        dto.setImageUrl("");
                    } else {
                        dto.setImageUrl(excursion.getImages().get(0).getImageUrl());
                    }

                    String transport = this.mapTransportTypeToString(excursion.getTransportType());
                    dto.setTransport(transport);

                    return dto;
                })
                .toList();
    }

    private String mapTransportTypeToString(TransportType transportType) {
        if (transportType.equals(TransportType.BUS)) {
            return "Автобус";
        } else if (transportType.equals(TransportType.PLANE)) {
            return "Самолет";
        } else if (transportType.equals(TransportType.CRUISE)) {
            return "Круиз";
        }

        return "";
    }

    @Override
    public ExcursionExportDTO getExcursionDetailsByName(String excursionName) {

        Optional<Excursion> optionalExcursion = this.excursionRepository.findByName(excursionName);

        if (optionalExcursion.isEmpty()) {
            return null;
        }

        Excursion excursion = optionalExcursion.get();

        ExcursionExportDTO excursionExportDTO = new ExcursionExportDTO();

        excursionExportDTO.setName(excursion.getName());
        excursionExportDTO.setPrice(excursion.getPrice());
        excursionExportDTO.setDate(excursion.getDate());
        excursionExportDTO.setDestination(excursion.getDestination().getName());
        excursionExportDTO.setEndurance(excursion.getProgram().getEndurance());

        int reservationsCount = Optional.ofNullable(excursion.getReservations())
                .orElse(Collections.emptyList()).stream()
                .mapToInt(Reservation::getTouristsCount)
                .sum();

        excursionExportDTO.setReservations(reservationsCount);
        excursionExportDTO.setTransport(this.mapTransportTypeToString(excursion.getTransportType()));
        excursionExportDTO.setImages(excursion.getImages().stream().map(Image::getImageUrl).toList());

        List<DayExportDTO> dayExportDTOList = excursion.getProgram().getDays().stream()
                .map(day -> {
                    DayExportDTO dayExportDTO = new DayExportDTO();

                    dayExportDTO.setDayNumber(day.getDayNumber());
                    dayExportDTO.setDescription(day.getDescription());

                    return dayExportDTO;
                })
                .toList();

        excursionExportDTO.setDays(dayExportDTOList);

        return excursionExportDTO;
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

        Program program = this.mapAddExcursionDTOToProgram(addExcursionDTO);
        Excursion excursion = this.mapAddExcursionDTOToExcursion(addExcursionDTO, program, optionalDestination.get());
        List<Day> dayList = this.mapStringListToDayList(addExcursionDTO.getDays(), program);

        program.setDays(dayList);
        program.setExcursion(excursion);
        this.programService.saveAndFlushProgram(program);

        return new Result(true, "Успешно добавихте нова екскурзия!");
    }

    @Override
    public Optional<Excursion> findExcursionByExcursionName(String excursionName) {
        return this.excursionRepository.findByName(excursionName);
    }

    @Override
    public void saveAndFlushExcursion(Excursion excursion) {
        this.excursionRepository.saveAndFlush(excursion);
    }

    @Override
    public List<String> getAllExcursionsNames() {
        return this.excursionRepository.findAll().stream().map(Excursion::getName).toList();
    }

    private Program mapAddExcursionDTOToProgram(AddExcursionDTO addExcursionDTO) {
        Program program = new Program();

        program.setEndurance(addExcursionDTO.getEndurance());
        program.setDays(new ArrayList<>());

        this.programService.saveAndFlushProgram(program);

        return program;
    }

    private Excursion mapAddExcursionDTOToExcursion(AddExcursionDTO addExcursionDTO, Program program, Destination destination) {
        Excursion excursion = new Excursion();

        excursion.setName(addExcursionDTO.getName());
        excursion.setDate(addExcursionDTO.getDate());
        excursion.setPrice(addExcursionDTO.getPrice());
        excursion.setTransportType(addExcursionDTO.getTransportType());
        excursion.setDestination(destination);
        excursion.setProgram(program);
        excursion.setReservations(new ArrayList<>());

        try {
            List<String> imageUrls = this.imageService.saveImages(addExcursionDTO.getImages());

            List<Image> images = new ArrayList<>();
            for (String imageUrl : imageUrls) {
                Image image = new Image();
                image.setImageUrl(imageUrl);
                image.setExcursion(excursion);
                images.add(image);
            }
            excursion.setImages(images);
        } catch (IOException e) {
            return null;
        }

        this.excursionRepository.saveAndFlush(excursion);

        return excursion;
    }

    private List<Day> mapStringListToDayList(List<String> days, Program program) {
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
