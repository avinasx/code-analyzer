package com.example.app.service;

import com.example.app.model.internal.RentalModel;
import com.example.app.model.request.RentalRequestModel;

import java.util.List;
import java.util.Optional;

public interface RentalService {

    List<RentalModel> getAllRentals();

    Optional<RentalModel> getRentalById(String id);

    List<RentalModel> getAllRentalsDetail();

    Optional<RentalModel> getRentalDetailById(String id);

    RentalModel addRental(RentalRequestModel model);

    RentalModel updateRental(String id, RentalRequestModel model);

    void removeRentalById(String id);
}
