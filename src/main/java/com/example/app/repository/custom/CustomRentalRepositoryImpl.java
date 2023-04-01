package com.example.app.repository.custom;

import com.example.app.model.internal.core.RentalModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomRentalRepositoryImpl implements CustomRentalRepository {
    @Override
    public List<RentalModel> findAllRentalDetailsList() {
        return null;
    }

    @Override
    public Optional<RentalModel> findRentalDetailsById(Integer rentalId) {
        return Optional.empty();
    }
}
