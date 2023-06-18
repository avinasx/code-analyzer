package com.example.app.app.store.repository;

import com.example.app.app.store.domain.entity.InventoryEntity;
import com.example.app.app.store.repository.custom.CustomInventoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Integer>, CustomInventoryRepository {
}
