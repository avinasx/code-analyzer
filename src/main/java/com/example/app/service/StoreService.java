package com.example.app.service;

import com.example.app.model.entity.StoreEntity;
import com.example.app.model.internal.StoreDetailModel;
import com.example.app.model.request.StoreRequestModel;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<StoreEntity> getAllStores();

    Optional<StoreEntity> getStoreById(String id);

    List<StoreDetailModel> getAllStoresDetail();

    Optional<StoreDetailModel> getStoreDetailById(String id);

    StoreEntity addStore(StoreRequestModel model);

    StoreEntity updateStore(String id, StoreRequestModel model);

    void deleteStoreById(String id);
}
