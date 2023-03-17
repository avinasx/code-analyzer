package com.example.app.repository.custom;

import com.example.app.model.entity.CityEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomCityRepositoryImpl implements CustomCityRepository {
    @Override
    public List<CityEntity> findAllCitiesDetail() {
        return null;
    }

    @Override
    public Optional<CityEntity> findCityDetailById(Integer id) {
        return Optional.empty();
    }
}
