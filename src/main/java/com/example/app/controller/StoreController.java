package com.example.app.controller;

import com.example.app.hateoas.assembler.StoreDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.StoreRepresentationModelAssembler;
import com.example.app.model.entity.StoreEntity;
import com.example.app.model.request.StoreRequestModel;
import com.example.app.model.response.StoreDetailResponseModel;
import com.example.app.model.response.StoreResponseModel;
import com.example.app.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final StoreRepresentationModelAssembler storeAssembler;
    private final StoreDetailRepresentationModelAssembler storeDetailAssembler;

    @GetMapping(path = "/stores")
    public ResponseEntity<CollectionModel<StoreResponseModel>> getAllStores() {
        return ResponseEntity.ok(storeAssembler.toCollectionModel(storeService.getAllStores()));
    }

    @PostMapping(path = "/stores")
    public ResponseEntity<Void> addStore(@RequestBody StoreRequestModel model) {
        var entity = new StoreEntity();
        BeanUtils.copyProperties(model, entity);
        var result = storeService.addStore(entity);
        return ResponseEntity.created(linkTo(methodOn(StoreController.class)
                .getStore(String.valueOf(result.getStoreId()))).toUri()).build();
    }

    @GetMapping(path = "/stores/{id}")
    public ResponseEntity<StoreResponseModel> getStore(@PathVariable String id) {
        return storeService.getStoreById(Integer.valueOf(id))
                .map(storeAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/stores/{id}")
    public ResponseEntity<Void> updateStore(@PathVariable String id, @ModelAttribute StoreRequestModel model) {
        var entity = new StoreEntity();
        BeanUtils.copyProperties(model, entity);
        entity.setStoreId(Integer.valueOf(id));
        var reuslt = storeService.updateStore(entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable String id) {
        storeService.deleteStoreById(Integer.valueOf(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/stores/{id}/details")
    public ResponseEntity<StoreDetailResponseModel> getStoreDetail(@PathVariable String id) {
        return storeService.getStoreDetailById(Integer.valueOf(id))
                .map(storeDetailAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
