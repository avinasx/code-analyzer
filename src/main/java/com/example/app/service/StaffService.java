package com.example.app.service;

import com.example.app.model.internal.StaffDetailModel;
import com.example.app.model.internal.StaffModel;
import com.example.app.model.request.StaffRequestModel;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    List<StaffModel> getAllStaffs();

    Optional<StaffModel> getStaffById(String id);

    List<StaffDetailModel> getAllStaffsDetail();

    Optional<StaffDetailModel> getStaffDetailById(String id);

    StaffModel addStaff(StaffRequestModel model);

    StaffModel updateStaff(String id, StaffRequestModel model);

    void removeStaffById(String id);
}
