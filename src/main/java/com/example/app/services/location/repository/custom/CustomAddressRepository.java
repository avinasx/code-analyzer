package com.example.app.services.location.repository.custom;

import com.example.app.services.location.domain.dto.AddressDto;

import java.util.List;
import java.util.Optional;

public interface CustomAddressRepository {
    List<AddressDto.Address> findAllAddressDetailsList();

    Optional<AddressDto.Address> findAddressDetailsById(Integer addressId);
}
