package com.youssef.grpc.hotel.controller;

import com.youssef.grpc.hotel.model.*;
import com.youssef.grpc.hotel.service.*;
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

