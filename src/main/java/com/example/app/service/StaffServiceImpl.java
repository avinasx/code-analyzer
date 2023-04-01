package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.core.StaffModel;
import com.example.app.model.internal.extra.StaffDetailsModel;
import com.example.app.model.mapping.mapper.StaffMapper;
import com.example.app.model.request.StaffRequestModel;
import com.example.app.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    @Override
    public List<StaffModel> getStaffList() {
        var list = staffRepository.findAll();
        return staffMapper.mapToDtoList(list);
    }

    @Override
    public Optional<StaffModel> getStaff(String staffId) {
        var entity = staffRepository.findById(Integer.valueOf(staffId)).orElseThrow(() ->
                new ResourceNotFoundException("Staff not found with id '" + staffId + "'"));
        return Optional.of(staffMapper.mapToDto(entity));
    }

    @Override
    public List<StaffDetailsModel> getStaffDetailsList() {
        return staffRepository.findAllStaffDetailsList();
    }

    @Override
    public Optional<StaffDetailsModel> getStaffDetails(String staffId) {
        var model = staffRepository.findStaffDetailsById(Integer.valueOf(staffId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Staff not found with id '" + staffId + "'");
        }
        return model;
    }

    @Override
    public StaffModel addStaff(StaffRequestModel model) {
        var entity = staffMapper.mapToEntity(model);
        var savedEntity = staffRepository.save(entity);
        return staffMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public StaffModel updateStaff(String staffId, StaffRequestModel model) {
        var entity = staffRepository.findById(Integer.valueOf(staffId)).orElseThrow(() ->
                new ResourceNotFoundException("Staff not found with id '" + staffId + "'"));
        entity.update(staffMapper.mapToEntity(model));
        return staffMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void removeStaff(String staffId) {
        staffRepository.deleteById(Integer.valueOf(staffId));
    }
}
