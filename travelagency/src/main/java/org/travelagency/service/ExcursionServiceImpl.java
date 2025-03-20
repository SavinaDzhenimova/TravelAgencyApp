package org.travelagency.service;

import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.*;
import org.travelagency.model.enums.RoleName;
import org.travelagency.model.enums.TransportType;
import org.travelagency.model.exportDTO.day.DayExportDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionExportDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionViewDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionViewInfo;
import org.travelagency.model.importDTO.*;
import org.travelagency.repository.ExcursionRepository;
import org.travelagency.service.events.AddExcursionEvent;
import org.travelagency.service.interfaces.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcursionServiceImpl implements ExcursionService {

    private final ExcursionRepository excursionRepository;
    private final ProgramService programService;
    private final DayService dayService;
    private final DestinationService destinationService;
    private final ImageService imageService;
    private final SubscriberService subscriberService;
    private final EmailService emailService;
    private final EmployeeService employeeService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ExcursionServiceImpl(ExcursionRepository excursionRepository, ProgramService programService, DayService dayService,
                                DestinationService destinationService, ImageService imageService,
                                SubscriberService subscriberService, EmailService emailService,
                                EmployeeService employeeService, ApplicationEventPublisher applicationEventPublisher) {
        this.excursionRepository = excursionRepository;
        this.programService = programService;
        this.dayService = dayService;
        this.destinationService = destinationService;
        this.imageService = imageService;
        this.subscriberService = subscriberService;
        this.emailService = emailService;
        this.employeeService = employeeService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void sendInquiryEmail(AddInquiryDTO addInquiryDTO, String excursionName) {
        this.emailService.sendInquiryEmail(
                addInquiryDTO.getName(),
                addInquiryDTO.getEmail(),
                addInquiryDTO.getPhone(),
                addInquiryDTO.getMessage(),
                excursionName);
    }

    @Override
    public ExcursionViewInfo getExcursionsForIndexPage() {
        List<Excursion> excursions = this.excursionRepository.findAll();

        List<ExcursionViewDTO> excursionExportList = excursions.stream()
                .sorted(Comparator.comparing(Excursion::getId).reversed())
                .map(this::mapExcursionToExcursionViewDTO)
                .limit(3)
                .toList();

        return new ExcursionViewInfo(excursionExportList);
    }

    @Override
    public Page<ExcursionViewDTO> getAllExcursions(Pageable pageable) {
        Page<Excursion> excursionsPage = this.excursionRepository.findAll(pageable);

        return excursionsPage.map(this::mapExcursionToExcursionViewDTO);
    }

    @Override
    public Page<ExcursionViewDTO> getAllExcursionsForLoggedEmployee(Pageable pageable) {

        Long loggedEmployeeId = this.employeeService.getLoggedEmployeeId();

        Page<Excursion> excursionsPage = this.excursionRepository.findAllByGuideId(loggedEmployeeId, pageable);

        return excursionsPage.map(this::mapExcursionToExcursionViewDTO);
    }

    @Override
    public Page<ExcursionViewDTO> getAllExcursionsByDestinationName(String destinationName, Pageable pageable) {
        Page<Excursion> excursionsPage = this.excursionRepository.findExcursionByDestinationName(destinationName, pageable);

        return excursionsPage.map(this::mapExcursionToExcursionViewDTO);
    }

    private ExcursionViewDTO mapExcursionToExcursionViewDTO(Excursion excursion) {
        ExcursionViewDTO excursionViewDTO = new ExcursionViewDTO();

        excursionViewDTO.setName(excursion.getName());
        excursionViewDTO.setPrice(excursion.getPrice());
        excursionViewDTO.setDestination(excursion.getDestination().getName());
        excursionViewDTO.setEndurance(excursion.getProgram().getEndurance());

        if (excursion.getImages().isEmpty()) {
            excursionViewDTO.setImageUrl("");
        } else {
            excursionViewDTO.setImageUrl(excursion.getImages().get(0).getImageUrl());
        }

        excursionViewDTO.setTransport(this.mapTransportTypeToString(excursion.getTransportType()));

        return excursionViewDTO;
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

        excursionExportDTO.setId(excursion.getId());
        excursionExportDTO.setName(excursion.getName());
        excursionExportDTO.setPrice(excursion.getPrice());
        excursionExportDTO.setDates(excursion.getDates().stream().sorted().collect(Collectors.toList()));
        excursionExportDTO.setDestination(excursion.getDestination().getName());
        excursionExportDTO.setEndurance(excursion.getProgram().getEndurance());
        excursionExportDTO.setGuideName(excursion.getGuide().getFullName());

        int reservationsCount = Optional.ofNullable(excursion.getReservations())
                .orElse(Collections.emptyList()).stream()
                .mapToInt(Reservation::getTouristsCount)
                .sum();

        excursionExportDTO.setReservations(reservationsCount);
        excursionExportDTO.setTransport(this.mapTransportTypeToString(excursion.getTransportType()));
        excursionExportDTO.setImages(excursion.getImages().stream().map(Image::getImageUrl).toList());

        List<DayExportDTO> dayExportDTOList = this.mapDaysListToDayExportDTO(excursion.getProgram().getDays());

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

        this.subscriberService.getAllSubscribers().forEach(subscriber ->
                this.applicationEventPublisher.publishEvent(new AddExcursionEvent(this,
                        Objects.requireNonNull(excursion).getName(), subscriber.getEmail())));

        return new Result(true, "Успешно добавихте нова екскурзия!");
    }

    @Override
    public UpdateExcursionDTO getExcursionDetailsForUpdate(String excursionName) {
        UpdateExcursionDTO updateExcursionDTO = new UpdateExcursionDTO();

        Optional<Excursion> optionalExcursion = this.excursionRepository.findByName(excursionName);

        if (optionalExcursion.isEmpty()) {
            return null;
        }

        Excursion excursion = optionalExcursion.get();

        updateExcursionDTO.setExcursionName(excursion.getName());
        updateExcursionDTO.setPrice(excursion.getPrice());
        updateExcursionDTO.setDestination(excursion.getDestination().getName());
        updateExcursionDTO.setGuideName(excursion.getGuide().getFullName());
        updateExcursionDTO.setGuideId(excursion.getGuide().getId());
        updateExcursionDTO.setTransport(excursion.getTransportType());
        updateExcursionDTO.setDates(this.formatDates(excursion.getDates()));

        List<DayExportDTO> dayExportDTOList = this.mapDaysListToDayExportDTO(excursion.getProgram().getDays());

        updateExcursionDTO.setDays(dayExportDTOList);

        return updateExcursionDTO;
    }

    @Override
    public Result updateExcursion(UpdateExcursionDTO updateExcursionDTO, String decodedExcursionName) {

        Optional<Excursion> optionalExcursion = this.excursionRepository.findByName(decodedExcursionName);

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

        Set<LocalDate> newLocalDates = updateExcursionDTO.getDates().stream()
                .map(date -> LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .collect(Collectors.toSet());

        for (LocalDate localDate : newLocalDates) {
            if (localDate.isBefore(LocalDate.now())) {
                return new Result(false, "Датите, които въвеждате, трябва да бъдат в бъдещето!");
            }
        }

        excursion.setDates(newLocalDates);

        Program program = excursion.getProgram();
        this.updateProgram(program, updateExcursionDTO);

        excursion.setProgram(program);
        this.excursionRepository.saveAndFlush(excursion);

        return new Result(true, "Успешно редактирахте избраната от вас екскурзия!");
    }

    private void updateProgram(Program program, UpdateExcursionDTO updateExcursionDTO) {

        this.dayService.deleteAllDaysByProgramId(program.getId());

        List<Day> updatedDays = updateExcursionDTO.getDays().stream()
                .map(dayExportDTO -> {
                    Day day = new Day();

                    day.setDayNumber(dayExportDTO.getDayNumber());
                    day.setDescription(dayExportDTO.getDescription());
                    day.setProgram(program);

                    this.dayService.saveAndFlushDay(day);

                    return day;
                }).toList();

        program.setDays(updatedDays);
        this.programService.saveAndFlushProgram(program);
    }

    @Override
    public Result updateExcursionDates(UpdateExcursionDatesDTO updateExcursionDatesDTO, String decodedExcursionName) {

        Optional<Excursion> optionalExcursion = this.excursionRepository.findByName(decodedExcursionName);

        if (optionalExcursion.isEmpty()) {
            return new Result(false, "Екскурзията, която се опитвате да редактирате, не съществува!");
        }

        Excursion excursion = optionalExcursion.get();

        updateExcursionDatesDTO.getDates().forEach(localDate -> excursion.getDates().add(localDate));

        this.excursionRepository.saveAndFlush(excursion);

        return new Result(true, "Успешно добавихте нови дати към тази екскурзия!");
    }

    @Override
    public Result updateExcursionProgram(UpdateExcursionProgramDTO updateExcursionProgramDTO, String decodedExcursionName) {

        Optional<Excursion> optionalExcursion = this.excursionRepository.findByName(decodedExcursionName);

        if (optionalExcursion.isEmpty()) {
            return new Result(false, "Екскурзията, която се опитвате да редактирате, не съществува!");
        }

        Excursion excursion = optionalExcursion.get();
        Program program = excursion.getProgram();

        int newEndurance = updateExcursionProgramDTO.getDaysCount() + excursion.getProgram().getEndurance();
        this.addDaysToProgram(updateExcursionProgramDTO.getDays(), newEndurance, program);

        excursion.setProgram(program);
        this.excursionRepository.saveAndFlush(excursion);

        return new Result(true, "Успешно добавихте нови дни към програмата на тази екскурзия!");
    }

    @Override
    @Transactional
    public void deleteExcursionById(Long id) {
        this.excursionRepository.deleteExcursionById(id);
    }

    @Override
    public Optional<Excursion> findExcursionByExcursionName(String excursionName) {
        return this.excursionRepository.findByName(excursionName);
    }

    @Override
    public Optional<Excursion> findExcursionById(Long id) {
        return this.excursionRepository.findById(id);
    }

    @Override
    public void saveAndFlushExcursion(Excursion excursion) {
        this.excursionRepository.saveAndFlush(excursion);
    }

    @Override
    public Page<String> getAllExcursionsNames(Pageable pageable) {
        return this.excursionRepository.findAllExcursionsNames(pageable);
    }

    @Override
    public List<Excursion> findAllExcursionsByDestinationId(Long destinationId) {
        return this.excursionRepository.findAllByDestinationId(destinationId);
    }

    private List<String> formatDates(Set<LocalDate> dates) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return dates.stream()
                .map(date -> date.format(formatter))
                .collect(Collectors.toList());
    }

    private void addDaysToProgram(List<String> days, int newEndurance, Program program) {
        int endurance = program.getEndurance();

        List<Day> daysToAddList = new ArrayList<>();

        for (int i = 0; i < days.size(); i++) {
            Day day = new Day();

            day.setDayNumber(endurance + 1 + i);
            day.setDescription(days.get(i));
            day.setProgram(program);

            this.dayService.saveAndFlushDay(day);
            daysToAddList.add(day);
        }

        program.setEndurance(newEndurance);
        program.getDays().addAll(daysToAddList);

        this.programService.saveAndFlushProgram(program);
    }

    private List<DayExportDTO> mapDaysListToDayExportDTO(List<Day> days) {
        return days.stream()
                .map(day -> {
                    DayExportDTO dayExportDTO = new DayExportDTO();

                    dayExportDTO.setId(day.getId());
                    dayExportDTO.setDayNumber(day.getDayNumber());
                    dayExportDTO.setDescription(day.getDescription());

                    return dayExportDTO;
                })
                .toList();
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
        excursion.setDates(addExcursionDTO.getDates());
        excursion.setPrice(addExcursionDTO.getPrice());
        excursion.setTransportType(addExcursionDTO.getTransportType());
        excursion.setDestination(destination);
        excursion.setProgram(program);
        excursion.setReservations(new ArrayList<>());

        Optional<Employee> optionalEmployee = this.employeeService.findEmployeeById(addExcursionDTO.getGuideId());

        if (optionalEmployee.isEmpty()) {
            return null;
        }

        Employee employee = optionalEmployee.get();

        if (employee.getRole().getRoleName().equals(RoleName.MANAGER)) {
            return null;
        }

        excursion.setGuide(employee);

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
