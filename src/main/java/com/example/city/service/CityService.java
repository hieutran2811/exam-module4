package com.example.city.service;

import com.example.city.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<City> findAllCity();
    Optional<City> findById(Long id);
    City save(City city);
    void remove(City city);
}
