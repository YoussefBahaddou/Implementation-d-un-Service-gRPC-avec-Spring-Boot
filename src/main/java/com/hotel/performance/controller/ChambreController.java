package com.hotel.performance.controller;

import com.hotel.performance.model.Chambre;
import com.hotel.performance.service.HotelService;
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
