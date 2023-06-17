package com.example.app.app.store.service;

import com.example.app.app.staff.domain.dto.StaffDto;
import com.example.app.app.store.domain.dto.StoreDetailsDto;
import com.example.app.app.store.domain.dto.StoreDto;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<StoreDto.Store> getStoreList();

    Optional<StoreDto.Store> getStore(String storeId);

    List<StoreDetailsDto.StoreDetails> getStoreDetailsList();

    Optional<StoreDetailsDto.StoreDetails> getStoreDetails(String storeId);

    List<StaffDto.Staff> getStoreStaffList(String storeId);

    Optional<StaffDto.Staff> getStoreStaff(String storeId, String staffId);

    StoreDto.Store addStore(StoreDto.StoreRequest model);

    StaffDto.Staff addStoreStaff(String storeId, String staffId);

    StoreDto.Store updateStore(String storeId, StoreDto.StoreRequest model);

    StaffDto.Staff updateStoreStaff(String storeId, String staffId);

    void deleteStore(String storeId);

    void removeStoreStaff(String storeId, String staffId);
}
