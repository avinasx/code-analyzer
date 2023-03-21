package com.example.app.service;

import com.example.app.model.internal.StoreDetailModel;
import com.example.app.model.internal.StoreModel;
import com.example.app.model.request.StoreRequestModel;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<StoreModel> getAllStores();

    Optional<StoreModel> getStoreById(String id);

    List<StoreDetailModel> getAllStoresDetail();

    Optional<StoreDetailModel> getStoreDetailById(String id);

    StoreModel addStore(StoreRequestModel model);

    StoreModel updateStore(String id, StoreRequestModel model);

    void deleteStoreById(String id);
}
