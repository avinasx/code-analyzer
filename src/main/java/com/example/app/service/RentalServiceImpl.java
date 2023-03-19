package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.RentalEntity;
import com.example.app.model.mapping.CopyUtils;
import com.example.app.model.request.RentalRequestModel;
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
    public Optional<RentalEntity> getRentalById(String id) {
        return rentalRepository.findById(Integer.valueOf(id));
    }

    @Override
    public List<RentalEntity> getAllRentalsDetail() {
        return rentalRepository.findAllRentalsDetail();
    }

    @Override
    public Optional<RentalEntity> getRentalDetailById(String id) {
        return rentalRepository.findRentalDetailById(Integer.valueOf(id));
    }

    @Override
    public RentalEntity addRental(RentalRequestModel model) {
        var entity = new RentalEntity();
        CopyUtils.copyNonNullProperties(model, entity);
        return rentalRepository.save(entity);
    }

    @Override
    public RentalEntity updateRental(String id, RentalRequestModel model) {
        var resource = rentalRepository.findById(Integer.valueOf(id));
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException("Rental not found with id '" + id + "'");
        }
        var entity = resource.get();
        CopyUtils.copyNonNullProperties(model, entity);
        return rentalRepository.save(entity);
    }

    @Override
    public void removeRentalById(String id) {
        rentalRepository.deleteById(Integer.valueOf(id));
    }
}
