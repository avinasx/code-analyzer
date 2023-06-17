package com.example.app.app.rental.repository.custom;

import com.example.app.app.rental.domain.dto.RentalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomRentalRepositoryImpl implements CustomRentalRepository {
    @Override
    public List<RentalDto.Rental> findAllRentalDetailsList() {
        return null;
    }

    @Override
    public Optional<RentalDto.Rental> findRentalDetailsById(Integer rentalId) {
        return Optional.empty();
    }
}
