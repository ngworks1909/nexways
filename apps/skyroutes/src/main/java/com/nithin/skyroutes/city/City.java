package com.nithin.skyroutes.city;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="city")
public class City {
    @Id
    private String cityId = UUID.randomUUID().toString().replace("-", "");

    @Column(nullable = false, unique = true)
    private String cityName;

    public City() { }
    public City(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }
    public String getCityName() {
        return cityName;
    }
}
