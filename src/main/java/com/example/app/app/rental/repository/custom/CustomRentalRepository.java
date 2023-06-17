package com.example.app.app.rental.repository.custom;

import com.example.app.app.rental.domain.dto.RentalDto;

import java.util.List;
import java.util.Optional;

public interface CustomRentalRepository {
    List<RentalDto.Rental> findAllRentalDetailsList();

    Optional<RentalDto.Rental> findRentalDetailsById(Integer rentalId);
}
