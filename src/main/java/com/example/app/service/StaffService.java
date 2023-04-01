package com.example.app.service;

import com.example.app.model.internal.core.StaffModel;
import com.example.app.model.internal.extra.StaffDetailsModel;
import com.example.app.model.request.StaffRequestModel;

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
