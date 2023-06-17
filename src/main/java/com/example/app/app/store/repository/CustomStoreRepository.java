package com.example.app.app.store.repository;

import com.example.app.app.staff.domain.dto.StaffDto;
import com.example.app.app.store.domain.dto.StoreDetailsDto;

import java.util.List;
import java.util.Optional;

public interface CustomStoreRepository {
    List<StoreDetailsDto.StoreDetails> findAllStoreDetailsList();

    Optional<StoreDetailsDto.StoreDetails> findStoreDetailsById(Integer storeId);

    List<StaffDto.Staff> findAllStoreStaffList(Integer storeId);

    Optional<StaffDto.Staff> findStoreStaffById(Integer storeId, Integer staffId);
}
