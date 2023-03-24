package com.example.app.repository.custom;

import com.example.app.model.internal.RentalModel;
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
    public List<RentalModel> findAllRentalsDetail() {
        return null;
    }

    @Override
    public Optional<RentalModel> findRentalDetailById(Integer id) {
        return Optional.empty();
    }
}
