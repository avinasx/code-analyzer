package com.example.app.repository.custom;

import com.example.app.model.internal.core.AddressModel;

import java.util.List;
import java.util.Optional;

public interface CustomAddressRepository {
    List<AddressModel> findAllAddressDetailsList();

    Optional<AddressModel> findAddressDetailsById(Integer addressId);
}
