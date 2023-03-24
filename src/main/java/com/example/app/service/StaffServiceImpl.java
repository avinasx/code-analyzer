package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.StaffDetailModel;
import com.example.app.model.internal.StaffModel;
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
    public List<StaffModel> getAllStaffs() {
        var list = staffRepository.findAll();
        return staffMapper.mapToDtoList(list);
    }

    @Override
    public Optional<StaffModel> getStaffById(String id) {
        var entity = staffRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Staff not found with id '" + id + "'"));
        return Optional.of(staffMapper.mapToDto(entity));
    }

    @Override
    public List<StaffDetailModel> getAllStaffsDetail() {
        return staffRepository.findAllStaffsDetail();
    }

    @Override
    public Optional<StaffDetailModel> getStaffDetailById(String id) {
        var model = staffRepository.findStaffDetailById(Integer.valueOf(id));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Staff not found with id '" + id + "'");
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
    public StaffModel updateStaff(String id, StaffRequestModel model) {
        var entity = staffRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Staff not found with id '" + id + "'"));
        entity.update(staffMapper.mapToEntity(model));
        return staffMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void removeStaffById(String id) {
        var entity = staffRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Staff not found with id '" + id + "'"));
        staffRepository.deleteById(entity.getStaffId());
    }
}
