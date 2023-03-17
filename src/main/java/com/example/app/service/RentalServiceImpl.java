package com.example.app.service;

import com.example.app.model.entity.RentalEntity;
import com.example.app.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {
    private RentalRepository rentalRepository;

    @Override
    public List<RentalEntity> getAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public Optional<RentalEntity> getRentalById(Integer id) {
        return rentalRepository.findById(id);
    }

    @Override
    public List<RentalEntity> getAllRentalsDetail() {
        return rentalRepository.findAllRentalsDetail();
    }

    @Override
    public Optional<RentalEntity> getRentalDetailById(Integer id) {
        return rentalRepository.findRentalDetailById(id);
    }

    @Override
    public void addRental(RentalEntity entity) {
        rentalRepository.save(entity);
    }

    @Override
    public void updateRental(RentalEntity entity) {
        rentalRepository.save(entity);
    }

    @Override
    public void removeRentalById(Integer id) {
        rentalRepository.deleteById(id);
    }
}
