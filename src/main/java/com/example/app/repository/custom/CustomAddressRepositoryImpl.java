package com.example.app.repository.custom;

import com.example.app.model.internal.AddressModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomAddressRepositoryImpl implements CustomAddressRepository {
    @Override
    public List<AddressModel> findAllAddressesDetail() {
        return null;
    }

    @Override
    public Optional<AddressModel> findAddressDetailById(Integer id) {
        return Optional.empty();
    }
}
