package com.hotel.performance.soap;

import com.hotel.performance.model.Reservation;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface SoapService {
    @WebMethod
    Reservation createReservation(@WebParam(name = "reservation") Reservation reservation,
            @WebParam(name = "clientId") Long clientId,
            @WebParam(name = "chambreId") Long chambreId);

    @WebMethod
    Reservation getReservation(@WebParam(name = "id") Long id);

    @WebMethod
    List<Reservation> getAllReservations();

    @WebMethod
    Reservation updateReservation(@WebParam(name = "id") Long id,
            @WebParam(name = "reservation") Reservation reservation);

    @WebMethod
    void deleteReservation(@WebParam(name = "id") Long id);
}
