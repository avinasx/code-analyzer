package com.example.app.app.rental.repository.custom;

import com.example.app.app.rental.domain.dto.RentalModel;

import java.util.List;
import java.util.Optional;

public interface CustomRentalRepository {
    List<RentalModel> findAllRentalDetailsList();

    Optional<RentalModel> findRentalDetailsById(Integer rentalId);
}
