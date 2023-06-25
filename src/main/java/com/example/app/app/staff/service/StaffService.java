package com.example.app.app.staff.service;

import com.example.app.app.staff.domain.dto.StaffDetailsDto;
import com.example.app.app.staff.domain.dto.StaffDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    List<StaffDto.Staff> getStaffList(Pageable pageable);

    Optional<StaffDto.Staff> getStaff(Integer staffId);

    List<StaffDetailsDto.StaffDetails> getStaffDetailsList();

    Optional<StaffDetailsDto.StaffDetails> getStaffDetails(Integer staffId);

    StaffDto.Staff addStaff(StaffDto.StaffRequest model);

    StaffDto.Staff updateStaff(Integer staffId, StaffDto.StaffRequest model);

    void removeStaff(Integer staffId);
}
