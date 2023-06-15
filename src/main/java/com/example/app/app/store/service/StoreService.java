package com.example.app.app.store.service;

import com.example.app.app.staff.domain.dto.StaffModel;
import com.example.app.app.staff.domain.dto.StoreDetailsModel;
import com.example.app.app.store.domain.dto.StoreModel;
import com.example.app.app.store.domain.dto.StoreRequestModel;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<StoreModel> getStoreList();

    Optional<StoreModel> getStore(String storeId);

    List<StoreDetailsModel> getStoreDetailsList();

    Optional<StoreDetailsModel> getStoreDetails(String storeId);

    List<StaffModel> getStoreStaffList(String storeId);

    Optional<StaffModel> getStoreStaff(String storeId, String staffId);

    StoreModel addStore(StoreRequestModel model);

    StaffModel addStoreStaff(String storeId, String staffId);

    StoreModel updateStore(String storeId, StoreRequestModel model);

    StaffModel updateStoreStaff(String storeId, String staffId);

    void deleteStore(String storeId);

    void removeStoreStaff(String storeId, String staffId);
}
