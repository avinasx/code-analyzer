package com.example.app.controller;

import com.example.app.hateoas.assembler.StaffRepresentationModelAssembler;
import com.example.app.hateoas.assembler.StoreDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.StoreRepresentationModelAssembler;
import com.example.app.model.request.StoreRequestModel;
import com.example.app.model.response.StaffResponseModel;
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
@RequestMapping(value = "/stores")
@AllArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final StoreRepresentationModelAssembler storeAssembler;
    private final StoreDetailRepresentationModelAssembler storeDetailAssembler;
    private final StaffRepresentationModelAssembler staffAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<StoreResponseModel>> getStores() {
        return ResponseEntity.ok(storeAssembler.toCollectionModel(
                storeService.getStores()));
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> addStore(@RequestBody StoreRequestModel model) {
        var result = storeService.addStore(model);
        return ResponseEntity.created(linkTo(methodOn(StoreController.class)
                .getStore(String.valueOf(result.getStoreId()))).toUri()).build();
    }

    @GetMapping(path = "/{storeId}")
    public ResponseEntity<StoreResponseModel> getStore(@PathVariable String storeId) {
        return storeService.getStore(storeId)
                .map(storeAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{storeId}")
    public ResponseEntity<Void> updateStore(@PathVariable String storeId, @ModelAttribute StoreRequestModel model) {
        var result = storeService.updateStore(storeId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable String storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping(path = "/{storeId}/inventory")
    // public ResponseEntity<CollectionModel<StoreResponseModel>> getStoreInventory(@PathVariable String storeId) {
    //     return storeService.getStoreInventory(storeId)
    //             .map(storeAssembler::toCollectionModel)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }
    //
    // @GetMapping(path = "/{storeId}/inventory/{filmId}")
    // public ResponseEntity<StoreResponseModel> getStoreInventory(@PathVariable String storeId, @PathVariable String filmId) {
    //     return storeService.getStoreInventory(storeId, filmId)
    //             .map(storeAssembler::toModel)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }

    @GetMapping(path = "/{storeId}/staffs")
    public ResponseEntity<CollectionModel<StaffResponseModel>> getStoreStaffs(@PathVariable String storeId) {
        return ResponseEntity.ok(staffAssembler.toCollectionModel(
                storeService.getStoreStaffs(storeId)));
    }

    @GetMapping(path = "/{storeId}/staffs/{staffId}")
    public ResponseEntity<StaffResponseModel> getStoreStaff(@PathVariable String storeId,
                                                            @PathVariable String staffId) {
        return storeService.getStoreStaff(storeId, staffId)
                .map(staffAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/{storeId}/staffs/{staffId}")
    public ResponseEntity<Void> addStoreStaff(@PathVariable String storeId, @PathVariable String staffId) {
        var result = storeService.addStoreStaff(storeId, staffId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{storeId}/staffs/{staffId}")
    public ResponseEntity<Void> updateStoreStaff(@PathVariable String storeId, @PathVariable String staffId) {
        var result = storeService.updateStoreStaff(storeId, staffId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{storeId}/staffs/{staffId}")
    public ResponseEntity<Void> deleteStoreStaff(@PathVariable String storeId, @PathVariable String staffId) {
        storeService.removeStoreStaff(storeId, staffId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{storeId}/details")
    public ResponseEntity<StoreDetailResponseModel> getStoreDetail(@PathVariable String storeId) {
        return storeService.getStoreDetail(storeId)
                .map(storeDetailAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
