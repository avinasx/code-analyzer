package com.example.app.app.rental.repository;

import com.example.app.app.rental.domain.entity.RentalEntity;
import com.example.app.app.rental.repository.custom.CustomRentalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Integer>, CustomRentalRepository {
}
