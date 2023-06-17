package com.example.app.app.location.repository.custom;

import com.example.app.app.location.domain.dto.CityDto;

import java.util.List;
import java.util.Optional;

public interface CustomCityRepository {
    List<CityDto.City> findAllCityDetailsList();

    Optional<CityDto.City> findCityDetailsById(Integer cityId);
}
