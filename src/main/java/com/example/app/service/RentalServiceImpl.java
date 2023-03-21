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
        var result = rentalRepository.findAll();
        return rentalMapper.mapToDtoList(result);
    }

    @Override
    public Optional<RentalModel> getRentalById(String id) {
        var result = rentalRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Rental not found with id '" + id + "'"));
        return Optional.of(rentalMapper.mapToDto(entity));
    }

    @Override
    public List<RentalModel> getAllRentalsDetail() {
        return rentalRepository.findAllRentalsDetail();
    }

    @Override
    public Optional<RentalModel> getRentalDetailById(String id) {
        var result = rentalRepository.findRentalDetailById(Integer.valueOf(id));
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Rental not found with id '" + id + "'");
        }
        return result;
    }

    @Override
    public RentalModel addRental(RentalRequestModel model) {
        var entity = rentalMapper.mapToEntity(model);
        var result = rentalRepository.save(entity);
        return rentalMapper.mapToDto(result);
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
