package com.example.app.repository.custom;

import com.example.app.model.internal.AddressModel;

import java.util.List;
import java.util.Optional;

public interface CustomAddressRepository {
    List<AddressModel> findAllAddressesDetail();

    Optional<AddressModel> findAddressDetailById(Integer id);
}
