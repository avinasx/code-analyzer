package com.example.app.app.location.repository.custom;

import com.example.app.app.location.domain.dto.CityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomCityRepositoryImpl implements CustomCityRepository {
    @Override
    public List<CityModel> findAllCityDetailsList() {
        return null;
    }

    @Override
    public Optional<CityModel> findCityDetailsById(Integer cityId) {
        return Optional.empty();
    }
}
