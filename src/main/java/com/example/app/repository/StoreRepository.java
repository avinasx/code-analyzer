package com.example.app.repository;

import com.example.app.model.entity.StoreEntity;
import com.example.app.repository.custom.CustomStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Integer>, CustomStoreRepository {
}
