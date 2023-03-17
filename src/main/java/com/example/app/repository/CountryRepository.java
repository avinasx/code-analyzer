package com.example.app.repository;

import com.example.app.model.entity.CountryEntity;
import com.example.app.repository.custom.CustomCountryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer>, CustomCountryRepository {
}
