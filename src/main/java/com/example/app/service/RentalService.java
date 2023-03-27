package com.example.app.service;

import com.example.app.model.internal.core.RentalModel;
import com.example.app.model.request.RentalRequestModel;

import java.util.List;
import java.util.Optional;

public interface RentalService {

    List<RentalModel> getRentals();

    Optional<RentalModel> getRental(String rentalId);

    List<RentalModel> getRentalsDetail();

    Optional<RentalModel> getRentalDetail(String rentalId);

    RentalModel addRental(RentalRequestModel model);

    RentalModel updateRental(String rentalId, RentalRequestModel model);

    void deleteRental(String rentalId);
}
