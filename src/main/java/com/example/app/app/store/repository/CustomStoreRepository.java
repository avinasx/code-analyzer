package com.example.app.app.store.repository;

import com.example.app.app.staff.domain.dto.StaffModel;
import com.example.app.app.staff.domain.dto.StoreDetailsModel;

import java.util.List;
import java.util.Optional;

public interface CustomStoreRepository {
    List<StoreDetailsModel> findAllStoreDetailsList();

    Optional<StoreDetailsModel> findStoreDetailsById(Integer storeId);

    List<StaffModel> findAllStoreStaffList(Integer storeId);

    Optional<StaffModel> findStoreStaffById(Integer storeId, Integer staffId);
}
