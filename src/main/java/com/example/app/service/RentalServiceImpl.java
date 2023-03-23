package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.RentalModel;
import com.example.app.model.mapping.mapper.RentalMapper;
import com.example.app.model.request.RentalRequestModel;
import com.example.app.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    @Override
    public List<RentalModel> getAllRentals() {
        var list = rentalRepository.findAll();
        return rentalMapper.mapToDtoList(list);
    }

    @Override
    public Optional<RentalModel> getRentalById(String id) {
        var entity = rentalRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Rental not found with id '" + id + "'"));
        return Optional.of(rentalMapper.mapToDto(entity));
    }

    @Override
    public List<RentalModel> getAllRentalsDetail() {
        return rentalRepository.findAllRentalsDetail();
    }

    @Override
    public Optional<RentalModel> getRentalDetailById(String id) {
        var model = rentalRepository.findRentalDetailById(Integer.valueOf(id));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Rental not found with id '" + id + "'");
        }
        return model;
    }

    @Override
    public RentalModel addRental(RentalRequestModel model) {
        var entity = rentalMapper.mapToEntity(model);
        var savedEntity = rentalRepository.save(entity);
        return rentalMapper.mapToDto(savedEntity);
    }

    @Override
    public RentalModel updateRental(String id, RentalRequestModel model) {
        var result = rentalRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Rental not found with id '" + id + "'"));
        rentalMapper.updateEntity(model, entity);
        var updated = rentalRepository.save(entity);
        return rentalMapper.mapToDto(updated);
    }

    @Override
    public void removeRentalById(String id) {
        rentalRepository.deleteById(Integer.valueOf(id));
    }
}
