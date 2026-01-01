package com.youssef.grpc.hotel.controller;

import com.youssef.grpc.hotel.model.*;
import com.youssef.grpc.hotel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rest")
public class ChambreController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/chambres")
    public Chambre createChambre(@RequestBody Chambre chambre) {
        return hotelService.createChambre(chambre);
    }
}

