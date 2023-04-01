package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.core.StaffModel;
import com.example.app.model.internal.core.StoreModel;
import com.example.app.model.internal.extra.StoreDetailsModel;
import com.example.app.model.mapping.mapper.StoreMapper;
import com.example.app.model.request.StoreRequestModel;
import com.example.app.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    @Override
    public List<StoreModel> getStoreList() {
        var list = storeRepository.findAll();
        return storeMapper.mapToDtoList(list);
    }

    @Override
    public Optional<StoreModel> getStore(String storeId) {
        var entity = storeRepository.findById(Integer.valueOf(storeId)).orElseThrow(() ->
                new ResourceNotFoundException("Store not found with id '" + storeId + "'"));
        return Optional.of(storeMapper.mapToDto(entity));
    }

    @Override
    public List<StoreDetailsModel> getStoreDetailsList() {
        return storeRepository.findAllStoreDetailsList();
    }

    @Override
    public Optional<StoreDetailsModel> getStoreDetails(String storeId) {
        var model = storeRepository.findStoreDetailsById(Integer.valueOf(storeId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Store not found with id '" + storeId + "'");
        }
        return model;
    }

    @Override
    public List<StaffModel> getStoreStaffList(String storeId) {
        return storeRepository.findAllStoreStaffList(Integer.valueOf(storeId));
    }

    @Override
    public Optional<StaffModel> getStoreStaff(String storeId, String staffId) {
        var model = storeRepository.findStoreStaffById(Integer.valueOf(storeId), Integer.valueOf(staffId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Staff not found with id '" + staffId + "'");
        }
        return model;
    }

    @Override
    public StoreModel addStore(StoreRequestModel model) {
        var entity = storeMapper.mapToEntity(model);
        var savedEntity = storeRepository.save(entity);
        return storeMapper.mapToDto(savedEntity);
    }

    @Override
    public StaffModel addStoreStaff(String storeId, String staffId) {
        return null;
    }

    @Override
    @Transactional
    public StoreModel updateStore(String storeId, StoreRequestModel model) {
        var entity = storeRepository.findById(Integer.valueOf(storeId)).orElseThrow(() ->
                new ResourceNotFoundException("Store not found with id '" + storeId + "'"));
        entity.update(storeMapper.mapToEntity(model));
        return storeMapper.mapToDto(entity);
    }

    @Override
    public StaffModel updateStoreStaff(String storeId, String staffId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteStore(String storeId) {
        storeRepository.deleteById(Integer.valueOf(storeId));
    }

    @Override
    public void removeStoreStaff(String storeId, String staffId) {

    }
}
