package com.example.app.service;

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
    public void addStore(StoreEntity entity) {
        storeRepository.save(entity);
    }

    @Override
    public void updateStore(StoreEntity entity) {
        storeRepository.save(entity);
    }

    @Override
    public void deleteStoreById(Integer id) {
        storeRepository.deleteById(id);
    }
}
