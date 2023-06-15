package com.example.app.app.location.repository.custom;

import com.example.app.app.location.domain.dto.CityModel;

import java.util.List;
import java.util.Optional;

public interface CustomCityRepository {
    List<CityModel> findAllCityDetailsList();

    Optional<CityModel> findCityDetailsById(Integer cityId);
}
