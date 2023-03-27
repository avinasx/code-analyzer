package com.example.app.repository.custom;

import com.example.app.model.internal.core.RentalModel;

import java.util.List;
import java.util.Optional;

public interface CustomRentalRepository {
    List<RentalModel> findAllRentalsDetail();

    Optional<RentalModel> findRentalDetailById(Integer rentalId);
}
