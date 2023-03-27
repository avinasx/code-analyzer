package com.example.app.repository.custom;

import com.example.app.model.internal.core.CityModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomCityRepositoryImpl implements CustomCityRepository {
    @Override
    public List<CityModel> findAllCitiesDetail() {
        return null;
    }

    @Override
    public Optional<CityModel> findCityDetailById(Integer cityId) {
        return Optional.empty();
    }
}
