package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Excursion;
import org.travelagency.model.entity.Reservation;
import org.travelagency.model.entity.Result;
import org.travelagency.model.enums.PaymentModel;
import org.travelagency.model.exportDTO.reservation.ReservationViewDTO;
import org.travelagency.model.exportDTO.reservation.ReservationViewInfo;
import org.travelagency.model.importDTO.AddReservationDTO;
import org.travelagency.repository.ReservationRepository;
import org.travelagency.service.interfaces.ExcursionService;
import org.travelagency.service.interfaces.ReservationService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ExcursionService excursionService;
    private final ModelMapper modelMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ExcursionService excursionService,
                                  ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.excursionService = excursionService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<ReservationViewInfo> getAllReservationsGroupedByExcursionsNames(Pageable pageable) {
        Page<String> excursionNames = this.excursionService.getAllExcursionsNames(pageable);

        List<ReservationViewInfo> reservationViewInfoList = excursionNames.getContent().stream()
                .map(this::mapReservationToReservationViewInfo)
                .collect(Collectors.toList());

        return new PageImpl<>(reservationViewInfoList, pageable, excursionNames.getTotalElements());
    }

    private ReservationViewInfo mapReservationToReservationViewInfo(String excursionName) {
        List<Reservation> reservations = this.reservationRepository.findReservationByExcursionName(excursionName);

        List<ReservationViewDTO> reservationViewDTOList = reservations.stream()
                .map(reservation -> {
                    ReservationViewDTO dto = this.modelMapper.map(reservation, ReservationViewDTO.class);

                    dto.setComments(Objects.requireNonNullElse(reservation.getComments(), "-"));
                    dto.setExcursionName(reservation.getExcursion().getName());
                    String payment = this.mapPaymentModelToString(reservation.getPaymentModel());
                    dto.setPayment(payment);

                    return dto;
                })
                .toList();

        int totalTourists = reservations.stream().mapToInt(Reservation::getTouristsCount).sum();

        ReservationViewInfo reservationViewInfo = new ReservationViewInfo(reservationViewDTOList);
        reservationViewInfo.setTotalTourists(totalTourists);
        reservationViewInfo.setExcursionName(excursionName);

        return reservationViewInfo;
    }

    private String mapPaymentModelToString(PaymentModel paymentModel) {
        if (paymentModel.equals(PaymentModel.IN_CASH)) {
            return "В брой";
        } else if (paymentModel.equals(PaymentModel.BANK_PAYMENT)) {
            return "Банков превод";
        } else if (paymentModel.equals((PaymentModel.CREDIT_CARD))) {
            return "Кредитна карта";
        }

        return "";
    }

    @Override
    public Result addReservation(AddReservationDTO addReservationDTO, String excursionName) {
        if (addReservationDTO == null) {
            return new Result(false, "Резервацията не съществува!");
        }

        Optional<Excursion> optionalExcursion = this.excursionService.findExcursionByExcursionName(excursionName);

        if (optionalExcursion.isEmpty()) {
            return new Result(false, "Екскурзията, за която се опитвате да резервирате не съществува");
        }

        Excursion excursion = optionalExcursion.get();

        Reservation reservation = new Reservation();

        reservation.setFullName(addReservationDTO.getFullName());
        reservation.setPhoneNumber(addReservationDTO.getPhoneNumber());
        reservation.setEmail(addReservationDTO.getEmail());
        reservation.setTouristsCount(addReservationDTO.getTouristsCount());
        reservation.setComments(addReservationDTO.getComments());
        reservation.setPaymentModel(addReservationDTO.getPayment());
        reservation.setDate(LocalDate.now());
        reservation.setExcursionDate(addReservationDTO.getExcursionDate());

        List<String> tourists = this.getTouristsForReservation(addReservationDTO.getTouristNames());

        if (tourists.size() != addReservationDTO.getTouristsCount()) {
            return new Result(false, "Не сте посочили съвместим брой туристи и имена!");
        }

        reservation.setTouristNames(tourists);
        reservation.setExcursion(excursion);

        this.reservationRepository.saveAndFlush(reservation);

        excursion.getReservations().add(reservation);
        this.excursionService.saveAndFlushExcursion(excursion);

        return new Result(true, "Успешно направихте своята резервация за " +
                addReservationDTO.getTouristsCount() + " човека! " +
                "Очаквайте обаждане от наш служител за потвърждение на резервацията!");
    }

    private List<String> getTouristsForReservation(String touristNames) {
        if (touristNames == null || touristNames.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(touristNames.split("\\s*,\\s*"))
                .filter(name -> !name.isBlank())
                .collect(Collectors.toList());
    }
}
