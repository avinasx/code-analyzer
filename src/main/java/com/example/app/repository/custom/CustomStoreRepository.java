package com.example.app.repository.custom;

import com.example.app.model.internal.core.StaffModel;
import com.example.app.model.internal.extra.StoreDetailsModel;

import java.util.List;
import java.util.Optional;

public interface CustomStoreRepository {
    List<StoreDetailsModel> findAllStoreDetailsList();

    Optional<StoreDetailsModel> findStoreDetailsById(Integer storeId);

    List<StaffModel> findAllStoreStaffList(Integer storeId);

    Optional<StaffModel> findStoreStaffById(Integer storeId, Integer staffId);
}
