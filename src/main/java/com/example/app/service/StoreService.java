package com.example.app.service;

import com.example.app.model.entity.StoreEntity;
import com.example.app.model.internal.StoreDetailModel;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<StoreEntity> getAllStores();

    Optional<StoreEntity> getStoreById(Integer id);

    List<StoreDetailModel> getAllStoresDetail();

    Optional<StoreDetailModel> getStoreDetailById(Integer id);

    void addStore(StoreEntity entity);

    void updateStore(StoreEntity entity);

    void deleteStoreById(Integer id);
}
