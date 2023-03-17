package com.example.app.controller;

import com.example.app.hateoas.assembler.StaffDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.StaffRepresentationModelAssembler;
import com.example.app.model.entity.StaffEntity;
import com.example.app.model.response.StaffDetailResponseModel;
import com.example.app.model.response.StaffResponseModel;
import com.example.app.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class StaffController {
    private StaffService staffService;
    private StaffRepresentationModelAssembler staffAssembler;
    private StaffDetailRepresentationModelAssembler staffDetailAssembler;

    @GetMapping(path = "/staffs")
    public ResponseEntity<CollectionModel<StaffResponseModel>> getAllStaffs() {
        return ResponseEntity.ok(staffAssembler.toCollectionModel(staffService.getAllStaffs()));
    }

    @PostMapping(path = "/staffs")
    public ResponseEntity<Void> addStaff(@ModelAttribute StaffEntity entity) {
        staffService.addStaff(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/staffs/{id}")
    public ResponseEntity<StaffResponseModel> getStaff(@PathVariable String id) {
        return staffService.getStaffById(Integer.valueOf(id))
                .map(staffAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/staffs/{id}")
    public ResponseEntity<Void> updateStaff(@PathVariable String id, @ModelAttribute StaffEntity entity) {
        staffService.updateStaff(entity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/staffs/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable String id) {
        staffService.removeStaffById(Integer.valueOf(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/staffs/{id}/details")
    public ResponseEntity<StaffDetailResponseModel> getStaffDetail(@PathVariable String id) {
        return staffService.getStaffDetailById(Integer.valueOf(id))
                .map(staffDetailAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
