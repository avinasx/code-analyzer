package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.StoreDetailModel;
import com.example.app.model.internal.StoreModel;
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
    public List<StoreModel> getAllStores() {
        var list = storeRepository.findAll();
        return storeMapper.mapToDtoList(list);
    }

    @Override
    public Optional<StoreModel> getStoreById(String id) {
        var entity = storeRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Store not found with id '" + id + "'"));
        return Optional.of(storeMapper.mapToDto(entity));
    }

    @Override
    public List<StoreDetailModel> getAllStoresDetail() {
        return storeRepository.findAllStoresDetail();
    }

    @Override
    public Optional<StoreDetailModel> getStoreDetailById(String id) {
        var model = storeRepository.findStoreDetailById(Integer.valueOf(id));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Store not found with id '" + id + "'");
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
    @Transactional
    public StoreModel updateStore(String id, StoreRequestModel model) {
        var entity = storeRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Store not found with id '" + id + "'"));
        entity.update(storeMapper.mapToEntity(model));
        return storeMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void deleteStoreById(String id) {
        var entity = storeRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Store not found with id '" + id + "'"));
        storeRepository.deleteById(entity.getStoreId());
    }
}
