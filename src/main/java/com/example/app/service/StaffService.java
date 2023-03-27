package com.example.app.service;

import com.example.app.model.internal.core.StaffModel;
import com.example.app.model.internal.extra.StaffDetailModel;
import com.example.app.model.request.StaffRequestModel;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    List<StaffModel> getStaffs();

    Optional<StaffModel> getStaff(String staffId);

    List<StaffDetailModel> getStaffsDetail();

    Optional<StaffDetailModel> getStaffDetail(String staffId);

    StaffModel addStaff(StaffRequestModel model);

    StaffModel updateStaff(String staffId, StaffRequestModel model);

    void removeStaff(String staffId);
}
