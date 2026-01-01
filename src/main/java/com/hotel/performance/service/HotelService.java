package com.hotel.performance.service;

import com.hotel.performance.model.Chambre;
import com.hotel.performance.model.Client;
import com.hotel.performance.model.Reservation;
import com.hotel.performance.repository.ChambreRepository;
import com.hotel.performance.repository.ClientRepository;
import com.hotel.performance.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ChambreRepository chambreRepository;

    public Reservation createReservation(Reservation reservation, Long clientId, Long chambreId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        Chambre chambre = chambreRepository.findById(chambreId)
                .orElseThrow(() -> new RuntimeException("Chambre not found"));
        reservation.setClient(client);
        reservation.setChambre(chambre);
        return reservationRepository.save(reservation);
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Reservation reservation = getReservation(id);
        reservation.setDateDebut(reservationDetails.getDateDebut());
        reservation.setDateFin(reservationDetails.getDateFin());
        reservation.setPreferences(reservationDetails.getPreferences());
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    // Helpers to create dummy data if needed
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Chambre createChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }
}
