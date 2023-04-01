package com.example.app.repository.custom;

import com.example.app.model.internal.extra.StaffDetailsModel;

import java.util.List;
import java.util.Optional;

public interface CustomStaffRepository {
    List<StaffDetailsModel> findAllStaffDetailsList();

    Optional<StaffDetailsModel> findStaffDetailsById(Integer staffId);
}
