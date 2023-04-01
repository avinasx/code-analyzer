package com.example.app.repository.custom;

import com.example.app.model.internal.core.CityModel;

import java.util.List;
import java.util.Optional;

public interface CustomCityRepository {
    List<CityModel> findAllCityDetailsList();

    Optional<CityModel> findCityDetailsById(Integer cityId);
}
