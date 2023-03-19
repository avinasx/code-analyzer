package com.example.app.service;

import com.example.app.model.entity.RentalEntity;
import com.example.app.model.request.RentalRequestModel;

import java.util.List;
import java.util.Optional;

public interface RentalService {

    List<RentalEntity> getAllRentals();

    Optional<RentalEntity> getRentalById(String id);

    List<RentalEntity> getAllRentalsDetail();

    Optional<RentalEntity> getRentalDetailById(String id);

    RentalEntity addRental(RentalRequestModel model);

    RentalEntity updateRental(String id, RentalRequestModel model);

    void removeRentalById(String id);
}
