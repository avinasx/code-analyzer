package com.example.app.service;

import com.example.app.model.entity.StaffEntity;
import com.example.app.model.internal.StaffDetailModel;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    List<StaffEntity> getAllStaffs();

    Optional<StaffEntity> getStaffById(Integer id);

    List<StaffDetailModel> getAllStaffsDetail();

    Optional<StaffDetailModel> getStaffDetailById(Integer id);

    StaffEntity addStaff(StaffEntity entity);

    void updateStaff(StaffEntity entity);

    void removeStaffById(Integer id);
}
