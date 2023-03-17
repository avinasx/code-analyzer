package com.example.app.repository;

import com.example.app.model.entity.InventoryEntity;
import com.example.app.repository.custom.CustomInventoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Integer>, CustomInventoryRepository {
}
