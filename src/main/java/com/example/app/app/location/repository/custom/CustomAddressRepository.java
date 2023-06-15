package com.example.app.app.location.repository.custom;

import com.example.app.app.catalog.domain.dto.AddressModel;

import java.util.List;
import java.util.Optional;

public interface CustomAddressRepository {
    List<AddressModel> findAllAddressDetailsList();

    Optional<AddressModel> findAddressDetailsById(Integer addressId);
}
