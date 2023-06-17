package com.example.app.app.store.controller;

import com.example.app.app.staff.assembler.StaffRepresentationModelAssembler;
import com.example.app.app.staff.domain.dto.StaffDto;
import com.example.app.app.store.assembler.StoreDetailsRepresentationModelAssembler;
import com.example.app.app.store.assembler.StoreRepresentationModelAssembler;
import com.example.app.app.store.domain.dto.StoreDetailsDto;
import com.example.app.app.store.domain.dto.StoreDto;
import com.example.app.app.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final StoreRepresentationModelAssembler storeAssembler;
    private final StoreDetailsRepresentationModelAssembler storeDetailsAssembler;
    private final StaffRepresentationModelAssembler staffAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<StoreDto.StoreResponse>> getStoreList() {
        return ResponseEntity.ok(storeAssembler.toCollectionModel(
                storeService.getStoreList()));
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> addStore(@RequestBody StoreDto.StoreRequest model) {
        var result = storeService.addStore(model);
        return ResponseEntity.created(linkTo(methodOn(StoreController.class)
                .getStore(String.valueOf(result.getStoreId()))).toUri()).build();
    }

    @GetMapping(path = "/{storeId}")
    public ResponseEntity<StoreDto.StoreResponse> getStore(@PathVariable String storeId) {
        return storeService.getStore(storeId)
                .map(storeAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{storeId}")
    public ResponseEntity<Void> updateStore(@PathVariable String storeId, @ModelAttribute StoreDto.StoreRequest model) {
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
    public ResponseEntity<CollectionModel<StaffDto.StaffResponse>> getStoreStaffList(@PathVariable String storeId) {
        return ResponseEntity.ok(staffAssembler.toCollectionModel(
                storeService.getStoreStaffList(storeId)));
    }

    @GetMapping(path = "/{storeId}/staffs/{staffId}")
    public ResponseEntity<StaffDto.StaffResponse> getStoreStaff(@PathVariable String storeId,
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
    public ResponseEntity<StoreDetailsDto.StoreDetailsResponse> getStoreDetails(@PathVariable String storeId) {
        return storeService.getStoreDetails(storeId)
                .map(storeDetailsAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
