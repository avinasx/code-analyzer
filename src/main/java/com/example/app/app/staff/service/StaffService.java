package com.example.app.app.staff.service;

import com.example.app.app.staff.domain.dto.StaffDetailsModel;
import com.example.app.app.staff.domain.dto.StaffModel;
import com.example.app.app.staff.domain.dto.StaffRequestModel;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    List<StaffModel> getStaffList();

    Optional<StaffModel> getStaff(String staffId);

    List<StaffDetailsModel> getStaffDetailsList();

    Optional<StaffDetailsModel> getStaffDetails(String staffId);

    StaffModel addStaff(StaffRequestModel model);

    StaffModel updateStaff(String staffId, StaffRequestModel model);

    void removeStaff(String staffId);
}
