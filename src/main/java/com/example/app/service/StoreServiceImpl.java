package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.StoreEntity;
import com.example.app.model.internal.StoreDetailModel;
import com.example.app.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private StoreRepository storeRepository;

    @Override
    public List<StoreEntity> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Optional<StoreEntity> getStoreById(Integer id) {
        return storeRepository.findById(id);
    }

    @Override
    public List<StoreDetailModel> getAllStoresDetail() {
        return storeRepository.findAllStoresDetail();
    }

    @Override
    public Optional<StoreDetailModel> getStoreDetailById(Integer id) {
        return storeRepository.findStoreDetailById(id);
    }

    @Override
    public StoreEntity addStore(StoreEntity entity) {
        return storeRepository.save(entity);
    }

    @Override
    public StoreEntity updateStore(StoreEntity entity) {
        var id = entity.getStoreId();
        var resource = storeRepository.findById(id);
        if (resource.isPresent()) {
            return storeRepository.save(entity);
        } else {
            throw new ResourceNotFoundException("Store not found with id '" + id + "'");
        }
    }

    @Override
    public void deleteStoreById(Integer id) {
        storeRepository.deleteById(id);
    }
}
