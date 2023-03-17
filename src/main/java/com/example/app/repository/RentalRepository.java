package com.example.app.repository;

import com.example.app.model.entity.RentalEntity;
import com.example.app.repository.custom.CustomRentalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Integer>, CustomRentalRepository {
}
