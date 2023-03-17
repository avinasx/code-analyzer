package com.example.app.repository;

import com.example.app.model.entity.CityEntity;
import com.example.app.repository.custom.CustomCityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer>, CustomCityRepository {
}
