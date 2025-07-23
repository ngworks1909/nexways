package com.nithin.skyroutes.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nithin.skyroutes.city.CityService;



@RestController
@RequestMapping("/api/city")
public class CityRoute {

    @Autowired
    private CityService cityService;
    
    @GetMapping("/data")
    private ResponseEntity<?> fetchCities(){
        return ResponseEntity.status(200).body(Map.of(
                    "cities", cityService.fetchAllCities()
        ));
    }
    
}
