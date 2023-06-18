package com.example.app.app.store.repository;

import com.example.app.app.store.domain.entity.StoreEntity;
import com.example.app.app.store.repository.custom.CustomStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Integer>, CustomStoreRepository {
}
