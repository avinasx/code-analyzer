package com.example.app.app.staff.service;

import com.example.app.app.staff.domain.dto.StaffDetailsDto;
import com.example.app.app.staff.domain.dto.StaffDto;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    List<StaffDto.Staff> getStaffList();

    Optional<StaffDto.Staff> getStaff(String staffId);

    List<StaffDetailsDto.StaffDetails> getStaffDetailsList();

    Optional<StaffDetailsDto.StaffDetails> getStaffDetails(String staffId);

    StaffDto.Staff addStaff(StaffDto.StaffRequest model);

    StaffDto.Staff updateStaff(String staffId, StaffDto.StaffRequest model);

    void removeStaff(String staffId);
}
