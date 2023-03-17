package com.example.app.repository.custom;

import com.example.app.model.internal.StoreDetailModel;

import java.util.List;
import java.util.Optional;

public interface CustomStoreRepository {
    List<StoreDetailModel> findAllStoresDetail();

    Optional<StoreDetailModel> findStoreDetailById(Integer id);
}
