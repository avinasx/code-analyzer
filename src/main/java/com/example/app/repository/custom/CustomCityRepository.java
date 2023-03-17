package com.example.app.repository.custom;

import com.example.app.model.entity.CityEntity;

import java.util.List;
import java.util.Optional;

public interface CustomCityRepository {
    List<CityEntity> findAllCitiesDetail();

    Optional<CityEntity> findCityDetailById(Integer id);
}
