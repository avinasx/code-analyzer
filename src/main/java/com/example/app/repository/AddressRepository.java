package com.example.app.repository;

import com.example.app.model.entity.AddressEntity;
import com.example.app.repository.custom.CustomAddressRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer>, CustomAddressRepository {
}
