package com.example.app.repository.custom;

import com.example.app.model.entity.AddressEntity;

import java.util.List;
import java.util.Optional;

public interface CustomAddressRepository {
    List<AddressEntity> findAllAddressesDetail();

    Optional<AddressEntity> findAddressDetailById(Integer id);
}
