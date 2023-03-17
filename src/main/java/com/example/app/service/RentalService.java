package com.example.app.service;

import com.example.app.model.entity.RentalEntity;

import java.util.List;
import java.util.Optional;

public interface RentalService {

    List<RentalEntity> getAllRentals();

    Optional<RentalEntity> getRentalById(Integer id);

    List<RentalEntity> getAllRentalsDetail();

    Optional<RentalEntity> getRentalDetailById(Integer id);

    RentalEntity addRental(RentalEntity entity);

    void updateRental(RentalEntity entity);

    void removeRentalById(Integer id);
}
