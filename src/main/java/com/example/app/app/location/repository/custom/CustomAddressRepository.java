package com.example.app.app.location.repository.custom;

import com.example.app.app.location.domain.dto.AddressDto;

import java.util.List;
import java.util.Optional;

public interface CustomAddressRepository {
    List<AddressDto.Address> findAllAddressDetailsList();

    Optional<AddressDto.Address> findAddressDetailsById(Integer addressId);
}
