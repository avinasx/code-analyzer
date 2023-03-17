package com.example.app.service;

import com.example.app.model.entity.StaffEntity;
import com.example.app.model.internal.StaffDetailModel;
import com.example.app.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {
    private StaffRepository staffRepository;

    @Override
    public List<StaffEntity> getAllStaffs() {
        return staffRepository.findAll();
    }

    @Override
    public Optional<StaffEntity> getStaffById(Integer id) {
        return staffRepository.findById(id);
    }

    @Override
    public List<StaffDetailModel> getAllStaffsDetail() {
        return staffRepository.findAllStaffsDetail();
    }

    @Override
    public Optional<StaffDetailModel> getStaffDetailById(Integer id) {
        return staffRepository.findStaffDetailById(id);
    }

    @Override
    public void addStaff(StaffEntity entity) {
        staffRepository.save(entity);
    }

    @Override
    public void updateStaff(StaffEntity entity) {
        staffRepository.save(entity);
    }

    @Override
    public void removeStaffById(Integer id) {
        staffRepository.deleteById(id);
    }
}
