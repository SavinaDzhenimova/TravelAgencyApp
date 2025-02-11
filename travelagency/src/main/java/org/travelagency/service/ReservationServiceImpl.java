package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Excursion;
import org.travelagency.model.entity.Reservation;
import org.travelagency.model.entity.Result;
import org.travelagency.model.importDTO.AddReservationDTO;
import org.travelagency.repository.ReservationRepository;
import org.travelagency.service.interfaces.ExcursionService;
import org.travelagency.service.interfaces.ReservationService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ExcursionService excursionService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ExcursionService excursionService) {
        this.reservationRepository = reservationRepository;
        this.excursionService = excursionService;
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
