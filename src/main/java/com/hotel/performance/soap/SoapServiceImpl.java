package com.hotel.performance.soap;

import com.hotel.performance.model.Reservation;
import com.hotel.performance.service.HotelService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@WebService(endpointInterface = "com.hotel.performance.soap.SoapService")
public class SoapServiceImpl implements SoapService {

    @Autowired
    private HotelService hotelService;

    @Override
    public Reservation createReservation(Reservation reservation, Long clientId, Long chambreId) {
        return hotelService.createReservation(reservation, clientId, chambreId);
    }

    @Override
    public Reservation getReservation(Long id) {
        return hotelService.getReservation(id);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return hotelService.getAllReservations();
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservation) {
        return hotelService.updateReservation(id, reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        hotelService.deleteReservation(id);
    }
}
