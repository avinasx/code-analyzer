package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.StaffDetailModel;
import com.example.app.model.internal.StaffModel;
import com.example.app.model.mapping.mapper.StaffMapper;
import com.example.app.model.request.StaffRequestModel;
import com.example.app.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    @Override
    public List<StaffModel> getAllStaffs() {
        var result = staffRepository.findAll();
        return staffMapper.mapToDtoList(result);
    }

    @Override
    public Optional<StaffModel> getStaffById(String id) {
        var result = staffRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Staff not found with id '" + id + "'"));
        return Optional.of(staffMapper.mapToDto(entity));
    }

    @Override
    public List<StaffDetailModel> getAllStaffsDetail() {
        return staffRepository.findAllStaffsDetail();
    }

    @Override
    public Optional<StaffDetailModel> getStaffDetailById(String id) {
        var result = staffRepository.findStaffDetailById(Integer.valueOf(id));
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Staff not found with id '" + id + "'");
        }
        return result;
    }

    @Override
    public StaffModel addStaff(StaffRequestModel model) {
        var entity = staffMapper.mapToEntity(model);
        var result = staffRepository.save(entity);
        return staffMapper.mapToDto(result);
    }

    @Override
    public StaffModel updateStaff(String id, StaffRequestModel model) {
        var result = staffRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Staff not found with id '" + id + "'"));
        staffMapper.updateEntity(model, entity);
        var updated = staffRepository.save(entity);
        return staffMapper.mapToDto(updated);
    }

    @Override
    public void removeStaffById(String id) {
        staffRepository.deleteById(Integer.valueOf(id));
    }
}
