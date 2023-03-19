package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.StoreEntity;
import com.example.app.model.internal.StoreDetailModel;
import com.example.app.model.mapping.CopyUtils;
import com.example.app.model.request.StoreRequestModel;
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
    public Optional<StoreEntity> getStoreById(String id) {
        return storeRepository.findById(Integer.valueOf(id));
    }

    @Override
    public List<StoreDetailModel> getAllStoresDetail() {
        return storeRepository.findAllStoresDetail();
    }

    @Override
    public Optional<StoreDetailModel> getStoreDetailById(String id) {
        return storeRepository.findStoreDetailById(Integer.valueOf(id));
    }

    @Override
    public StoreEntity addStore(StoreRequestModel model) {
        var entity = new StoreEntity();
        CopyUtils.copyNonNullProperties(model, entity);
        return storeRepository.save(entity);
    }

    @Override
    public StoreEntity updateStore(String id, StoreRequestModel model) {
        var resource = storeRepository.findById(Integer.valueOf(id));
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException("Store not found with id '" + id + "'");
        }
        var entity = resource.get();
        CopyUtils.copyNonNullProperties(model, entity);
        return storeRepository.save(entity);
    }

    @Override
    public void deleteStoreById(String id) {
        storeRepository.deleteById(Integer.valueOf(id));
    }
}
