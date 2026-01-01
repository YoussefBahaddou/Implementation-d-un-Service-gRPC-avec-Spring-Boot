package com.hotel.performance.controller;

import com.hotel.performance.model.Reservation;
import com.hotel.performance.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphqlController {

    @Autowired
    private HotelService hotelService;

    @QueryMapping
    public Reservation getReservation(@Argument Long id) {
        return hotelService.getReservation(id);
    }

    @QueryMapping
    public List<Reservation> getAllReservations() {
        return hotelService.getAllReservations();
    }

    @MutationMapping
    public Reservation createReservation(@Argument ReservationInput reservationInput, @Argument Long clientId,
            @Argument Long chambreId) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(reservationInput.dateDebut());
        reservation.setDateFin(reservationInput.dateFin());
        reservation.setPreferences(reservationInput.preferences());
        return hotelService.createReservation(reservation, clientId, chambreId);
    }

    @MutationMapping
    public Reservation updateReservation(@Argument Long id, @Argument ReservationInput reservationInput) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(reservationInput.dateDebut());
        reservation.setDateFin(reservationInput.dateFin());
        reservation.setPreferences(reservationInput.preferences());
        return hotelService.updateReservation(id, reservation);
    }

    @MutationMapping
    public Boolean deleteReservation(@Argument Long id) {
        hotelService.deleteReservation(id);
        return true;
    }

    record ReservationInput(String dateDebut, String dateFin, String preferences) {
    }
}
