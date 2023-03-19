package com.example.app.controller;

import com.example.app.hateoas.assembler.StaffDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.StaffRepresentationModelAssembler;
import com.example.app.model.request.StaffRequestModel;
import com.example.app.model.response.StaffDetailResponseModel;
import com.example.app.model.response.StaffResponseModel;
import com.example.app.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class StaffController {
    private final StaffService staffService;
    private final StaffRepresentationModelAssembler staffAssembler;
    private final StaffDetailRepresentationModelAssembler staffDetailAssembler;

    @GetMapping(path = "/staffs")
    public ResponseEntity<CollectionModel<StaffResponseModel>> getAllStaffs() {
        return ResponseEntity.ok(staffAssembler.toCollectionModel(staffService.getAllStaffs()));
    }

    @PostMapping(path = "/staffs")
    public ResponseEntity<Void> addStaff(@RequestBody StaffRequestModel model) {
        var result = staffService.addStaff(model);
        return ResponseEntity.created(linkTo(methodOn(StaffController.class)
                .getStaff(String.valueOf(result.getStaffId()))).toUri()).build();
    }

    @GetMapping(path = "/staffs/{id}")
    public ResponseEntity<StaffResponseModel> getStaff(@PathVariable String id) {
        return staffService.getStaffById(id)
                .map(staffAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/staffs/{id}")
    public ResponseEntity<Void> updateStaff(@PathVariable String id, @RequestBody StaffRequestModel model) {
        var result = staffService.updateStaff(id, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/staffs/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable String id) {
        staffService.removeStaffById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/staffs/{id}/details")
    public ResponseEntity<StaffDetailResponseModel> getStaffDetail(@PathVariable String id) {
        return staffService.getStaffDetailById(id)
                .map(staffDetailAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
