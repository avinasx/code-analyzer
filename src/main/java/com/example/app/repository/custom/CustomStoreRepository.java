package com.example.app.repository.custom;

import com.example.app.model.internal.core.StaffModel;
import com.example.app.model.internal.extra.StoreDetailModel;

import java.util.List;
import java.util.Optional;

public interface CustomStoreRepository {
    List<StoreDetailModel> findAllStoresDetail();

    Optional<StoreDetailModel> findStoreDetailById(Integer storeId);

    List<StaffModel> findAllStoreStaffs(Integer storeId);

    Optional<StaffModel> findStoreStaffById(Integer storeId, Integer staffId);
}
