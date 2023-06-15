package com.example.app.app.location.repository.custom;

import com.example.app.app.catalog.domain.dto.AddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
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
