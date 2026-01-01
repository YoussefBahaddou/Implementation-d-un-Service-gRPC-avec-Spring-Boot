package com.hotel.performance.controller;

import com.hotel.performance.model.Reservation;
import com.hotel.performance.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest")
public class RestApiController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody Reservation reservation, @RequestParam Long clientId,
            @RequestParam Long chambreId) {
        return hotelService.createReservation(reservation, clientId, chambreId);
    }

    @GetMapping("/reservations/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        return hotelService.getReservation(id);
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return hotelService.getAllReservations();
    }

    @PutMapping("/reservations/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        return hotelService.updateReservation(id, reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        hotelService.deleteReservation(id);
    }
}
