package com.example.app.app.rental.service;

import com.example.app.app.rental.domain.dto.RentalDto;

import java.util.List;
import java.util.Optional;

public interface RentalService {

    List<RentalDto.Rental> getRentalList();

    Optional<RentalDto.Rental> getRental(String rentalId);

    List<RentalDto.Rental> getRentalDetailsList();

    Optional<RentalDto.Rental> getRentalDetails(String rentalId);

    RentalDto.Rental addRental(RentalDto.RentalRequest model);

    RentalDto.Rental updateRental(String rentalId, RentalDto.RentalRequest model);

    void deleteRental(String rentalId);
}
