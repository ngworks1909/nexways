package com.nithin.skyroutes.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<String> fetchAllCities() {
        return cityRepository.findAll().stream().map(City::getCityName).toList();
    }
}
