package com.hotel.performance.grpc;

import com.hotel.performance.model.Reservation;
import com.hotel.performance.service.HotelService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class GrpcServiceImpl extends HotelServiceGrpcGrpc.HotelServiceGrpcImplBase {

    @Autowired
    private HotelService hotelService;

    @Override
    public void createReservation(CreateReservationRequest request,
            StreamObserver<ReservationResponse> responseObserver) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(request.getDateDebut());
        reservation.setDateFin(request.getDateFin());
        reservation.setPreferences(request.getPreferences());

        Reservation saved = hotelService.createReservation(reservation, request.getClientId(), request.getChambreId());
        responseObserver.onNext(mapToResponse(saved));
        responseObserver.onCompleted();
    }

    @Override
    public void getReservation(GetReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
        Reservation reservation = hotelService.getReservation(request.getId());
        responseObserver.onNext(mapToResponse(reservation));
        responseObserver.onCompleted();
    }

    @Override
    public void updateReservation(UpdateReservationRequest request,
            StreamObserver<ReservationResponse> responseObserver) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(request.getDateDebut());
        reservation.setDateFin(request.getDateFin());
        reservation.setPreferences(request.getPreferences());

        Reservation updated = hotelService.updateReservation(request.getId(), reservation);
        responseObserver.onNext(mapToResponse(updated));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteReservation(DeleteReservationRequest request,
            StreamObserver<DeleteReservationResponse> responseObserver) {
        hotelService.deleteReservation(request.getId());
        responseObserver.onNext(DeleteReservationResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    private ReservationResponse mapToResponse(Reservation reservation) {
        return ReservationResponse.newBuilder()
                .setId(reservation.getId())
                .setClient(ClientResponse.newBuilder()
                        .setId(reservation.getClient().getId())
                        .setNom(reservation.getClient().getNom())
                        .setPrenom(reservation.getClient().getPrenom())
                        .setEmail(reservation.getClient().getEmail())
                        .setTelephone(reservation.getClient().getTelephone())
                        .build())
                .setChambre(ChambreResponse.newBuilder()
                        .setId(reservation.getChambre().getId())
                        .setType(reservation.getChambre().getType())
                        .setPrix(reservation.getChambre().getPrix())
                        .setDisponible(reservation.getChambre().getDisponible())
                        .build())
                .setDateDebut(reservation.getDateDebut())
                .setDateFin(reservation.getDateFin())
                .setPreferences(reservation.getPreferences())
                .build();
    }
}
