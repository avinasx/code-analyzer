package com.example.app.service;

import com.example.app.model.entity.StaffEntity;
import com.example.app.model.internal.StaffDetailModel;
import com.example.app.model.request.StaffRequestModel;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    List<StaffEntity> getAllStaffs();

    Optional<StaffEntity> getStaffById(String id);

    List<StaffDetailModel> getAllStaffsDetail();

    Optional<StaffDetailModel> getStaffDetailById(String id);

    StaffEntity addStaff(StaffRequestModel model);

    StaffEntity updateStaff(String id, StaffRequestModel model);

    void removeStaffById(String id);
}
