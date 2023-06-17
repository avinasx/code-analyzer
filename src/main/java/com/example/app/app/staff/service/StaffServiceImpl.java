package com.example.app.app.staff.service;

import com.example.app.app.staff.domain.dto.StaffDetailsDto;
import com.example.app.app.staff.domain.dto.StaffDto;
import com.example.app.app.staff.domain.mapper.StaffMapper;
import com.example.app.app.staff.repository.StaffRepository;
import com.example.app.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    @Override
    @Transactional(readOnly = true)
    public List<StaffDto.Staff> getStaffList() {
        var list = staffRepository.findAll();
        return staffMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StaffDto.Staff> getStaff(String staffId) {
        var entity = staffRepository.findById(Integer.valueOf(staffId)).orElseThrow(() ->
                new ResourceNotFoundException("Staff not found with id '" + staffId + "'"));
        return Optional.of(staffMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffDetailsDto.StaffDetails> getStaffDetailsList() {
        return staffRepository.findAllStaffDetailsList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StaffDetailsDto.StaffDetails> getStaffDetails(String staffId) {
        var model = staffRepository.findStaffDetailsById(Integer.valueOf(staffId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Staff not found with id '" + staffId + "'");
        }
        return model;
    }

    @Override
    @Transactional
    public StaffDto.Staff addStaff(StaffDto.StaffRequest model) {
        var entity = staffMapper.mapToEntity(model);
        var savedEntity = staffRepository.save(entity);
        return staffMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public StaffDto.Staff updateStaff(String staffId, StaffDto.StaffRequest model) {
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
