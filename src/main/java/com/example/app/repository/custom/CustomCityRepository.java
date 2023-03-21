package com.example.app.repository.custom;

import com.example.app.model.internal.CityModel;

import java.util.List;
import java.util.Optional;

public interface CustomCityRepository {
    List<CityModel> findAllCitiesDetail();

    Optional<CityModel> findCityDetailById(Integer id);
}
