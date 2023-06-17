package com.example.app.app.location.repository.custom;

import com.example.app.app.location.domain.dto.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomAddressRepositoryImpl implements CustomAddressRepository {
    @Override
    public List<AddressDto.Address> findAllAddressDetailsList() {
        return null;
    }

    @Override
    public Optional<AddressDto.Address> findAddressDetailsById(Integer addressId) {
        return Optional.empty();
    }
}
