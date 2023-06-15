package com.example.app.app.rental.service;

import com.example.app.app.rental.domain.dto.RentalModel;
import com.example.app.app.rental.domain.dto.RentalRequestModel;
import com.example.app.app.rental.domain.mapper.RentalMapper;
import com.example.app.app.rental.repository.RentalRepository;
import com.example.app.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RentalModel> getRentalList() {
        var list = rentalRepository.findAll();
        return rentalMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RentalModel> getRental(String rentalId) {
        var entity = rentalRepository.findById(Integer.valueOf(rentalId)).orElseThrow(() ->
                new ResourceNotFoundException("Rental not found with id '" + rentalId + "'"));
        return Optional.of(rentalMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalModel> getRentalDetailsList() {
        return rentalRepository.findAllRentalDetailsList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RentalModel> getRentalDetails(String rentalId) {
        var model = rentalRepository.findRentalDetailsById(Integer.valueOf(rentalId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Rental not found with id '" + rentalId + "'");
        }
        return model;
    }

    @Override
    @Transactional
    public RentalModel addRental(RentalRequestModel model) {
        var entity = rentalMapper.mapToEntity(model);
        var savedEntity = rentalRepository.save(entity);
        return rentalMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public RentalModel updateRental(String rentalId, RentalRequestModel model) {
        var entity = rentalRepository.findById(Integer.valueOf(rentalId)).orElseThrow(() ->
                new ResourceNotFoundException("Rental not found with id '" + rentalId + "'"));
        entity.update(rentalMapper.mapToEntity(model));
        return rentalMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void deleteRental(String rentalId) {
        rentalRepository.deleteById(Integer.valueOf(rentalId));
    }
}
