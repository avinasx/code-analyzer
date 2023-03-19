package com.example.app.controller;

import com.example.app.hateoas.assembler.StoreDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.StoreRepresentationModelAssembler;
import com.example.app.model.request.StoreRequestModel;
import com.example.app.model.response.StoreDetailResponseModel;
import com.example.app.model.response.StoreResponseModel;
import com.example.app.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
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
        var result = storeService.addStore(model);
        return ResponseEntity.created(linkTo(methodOn(StoreController.class)
                .getStore(String.valueOf(result.getStoreId()))).toUri()).build();
    }

    @GetMapping(path = "/stores/{id}")
    public ResponseEntity<StoreResponseModel> getStore(@PathVariable String id) {
        return storeService.getStoreById(id)
                .map(storeAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/stores/{id}")
    public ResponseEntity<Void> updateStore(@PathVariable String id, @ModelAttribute StoreRequestModel model) {
        var reuslt = storeService.updateStore(id, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable String id) {
        storeService.deleteStoreById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/stores/{id}/details")
    public ResponseEntity<StoreDetailResponseModel> getStoreDetail(@PathVariable String id) {
        return storeService.getStoreDetailById(id)
                .map(storeDetailAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
