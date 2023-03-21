package com.example.app.repository.custom;

import com.example.app.model.internal.RentalModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
