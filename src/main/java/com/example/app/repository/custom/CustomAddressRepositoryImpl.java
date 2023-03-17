package com.example.app.repository.custom;

import com.example.app.model.entity.AddressEntity;
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
    public List<AddressEntity> findAllAddressesDetail() {
        return null;
    }

    @Override
    public Optional<AddressEntity> findAddressDetailById(Integer id) {
        return Optional.empty();
    }
}
