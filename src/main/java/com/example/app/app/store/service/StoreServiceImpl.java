package com.example.app.app.store.service;

import com.example.app.app.staff.domain.dto.StaffDto;
import com.example.app.app.store.domain.dto.StoreDetailsDto;
import com.example.app.app.store.domain.dto.StoreDto;
import com.example.app.app.store.domain.mapper.StoreMapper;
import com.example.app.app.store.repository.StoreRepository;
import com.example.app.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<StoreDto.Store> getStoreList() {
        var list = storeRepository.findAll();
        return storeMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StoreDto.Store> getStore(String storeId) {
        var entity = storeRepository.findById(Integer.valueOf(storeId)).orElseThrow(() ->
                new ResourceNotFoundException("Store not found with id '" + storeId + "'"));
        return Optional.of(storeMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDetailsDto.StoreDetails> getStoreDetailsList() {
        return storeRepository.findAllStoreDetailsList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StoreDetailsDto.StoreDetails> getStoreDetails(String storeId) {
        var model = storeRepository.findStoreDetailsById(Integer.valueOf(storeId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Store not found with id '" + storeId + "'");
        }
        return model;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffDto.Staff> getStoreStaffList(String storeId) {
        return storeRepository.findAllStoreStaffList(Integer.valueOf(storeId));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StaffDto.Staff> getStoreStaff(String storeId, String staffId) {
        var model = storeRepository.findStoreStaffById(Integer.valueOf(storeId), Integer.valueOf(staffId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Staff not found with id '" + staffId + "'");
        }
        return model;
    }

    @Override
    @Transactional
    public StoreDto.Store addStore(StoreDto.StoreRequest model) {
        var entity = storeMapper.mapToEntity(model);
        var savedEntity = storeRepository.save(entity);
        return storeMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public StaffDto.Staff addStoreStaff(String storeId, String staffId) {
        return null;
    }

    @Override
    @Transactional
    public StoreDto.Store updateStore(String storeId, StoreDto.StoreRequest model) {
        var entity = storeRepository.findById(Integer.valueOf(storeId)).orElseThrow(() ->
                new ResourceNotFoundException("Store not found with id '" + storeId + "'"));
        entity.update(storeMapper.mapToEntity(model));
        return storeMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public StaffDto.Staff updateStoreStaff(String storeId, String staffId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteStore(String storeId) {
        storeRepository.deleteById(Integer.valueOf(storeId));
    }

    @Override
    @Transactional
    public void removeStoreStaff(String storeId, String staffId) {
    }
}
