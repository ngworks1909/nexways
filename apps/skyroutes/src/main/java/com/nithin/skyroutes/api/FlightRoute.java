package com.nithin.skyroutes.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/flights")
public class FlightRoute {

    @PostMapping("/create")
    public ResponseEntity<?> create() {

        
        
        return ResponseEntity.status(200).body(Map.of(
                "success", true,
                "message", "Flight created"
            ));
    }
    
}
