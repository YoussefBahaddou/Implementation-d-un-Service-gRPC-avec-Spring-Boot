package com.hotel.performance.controller;

import com.hotel.performance.model.Client;
import com.hotel.performance.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rest")
public class ClientController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/clients")
    public Client createClient(@RequestBody Client client) {
        return hotelService.createClient(client);
    }
}
