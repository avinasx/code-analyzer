package com.example.app.repository.custom;

import com.example.app.model.entity.RentalEntity;

import java.util.List;
import java.util.Optional;

public interface CustomRentalRepository {
    List<RentalEntity> findAllRentalsDetail();

    Optional<RentalEntity> findRentalDetailById(Integer id);
}
