package com.example.app.repository.custom;

import com.example.app.model.internal.core.AddressModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomAddressRepositoryImpl implements CustomAddressRepository {
    @Override
    public List<AddressModel> findAllAddressDetailsList() {
        return null;
    }

    @Override
    public Optional<AddressModel> findAddressDetailsById(Integer addressId) {
        return Optional.empty();
    }
}
