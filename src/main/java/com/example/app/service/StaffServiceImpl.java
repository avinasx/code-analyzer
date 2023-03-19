package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.StaffEntity;
import com.example.app.model.internal.StaffDetailModel;
import com.example.app.model.mapping.CopyUtils;
import com.example.app.model.request.StaffRequestModel;
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
    public Optional<StaffEntity> getStaffById(String id) {
        return staffRepository.findById(Integer.valueOf(id));
    }

    @Override
    public List<StaffDetailModel> getAllStaffsDetail() {
        return staffRepository.findAllStaffsDetail();
    }

    @Override
    public Optional<StaffDetailModel> getStaffDetailById(String id) {
        return staffRepository.findStaffDetailById(Integer.valueOf(id));
    }

    @Override
    public StaffEntity addStaff(StaffRequestModel model) {
        var entity = new StaffEntity();
        CopyUtils.copyNonNullProperties(model, entity);
        return staffRepository.save(entity);
    }

    @Override
    public StaffEntity updateStaff(String id, StaffRequestModel model) {
        var resource = staffRepository.findById(Integer.valueOf(id));
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException("Staff not found with id '" + id + "'");
        }
        var entity = resource.get();
        CopyUtils.copyNonNullProperties(model, entity);
        return staffRepository.save(entity);
    }

    @Override
    public void removeStaffById(String id) {
        staffRepository.deleteById(Integer.valueOf(id));
    }
}
