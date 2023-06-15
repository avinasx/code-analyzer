package com.example.app.app.staff.repository.custom;

import com.example.app.app.staff.domain.dto.StaffDetailsModel;

import java.util.List;
import java.util.Optional;

public interface CustomStaffRepository {
    List<StaffDetailsModel> findAllStaffDetailsList();

    Optional<StaffDetailsModel> findStaffDetailsById(Integer staffId);
}
