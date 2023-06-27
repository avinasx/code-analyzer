package com.example.app.services.store.repository.custom;

import com.example.app.services.staff.domain.dto.StaffDto;
import com.example.app.services.store.domain.dto.StoreDetailsDto;

import java.util.List;
import java.util.Optional;

public interface CustomStoreRepository {
    List<StoreDetailsDto.StoreDetails> findAllStoreDetailsList();

    Optional<StoreDetailsDto.StoreDetails> findStoreDetailsById(Integer storeId);

    List<StaffDto.Staff> findAllStoreStaffList(Integer storeId);

    Optional<StaffDto.Staff> findStoreStaffById(Integer storeId, Integer staffId);
}
