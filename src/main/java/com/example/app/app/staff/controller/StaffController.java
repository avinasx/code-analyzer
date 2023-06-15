package com.example.app.app.staff.controller;

import com.example.app.app.staff.assembler.StaffDetailsRepresentationModelAssembler;
import com.example.app.app.staff.assembler.StaffRepresentationModelAssembler;
import com.example.app.app.staff.domain.dto.StaffDetailResponseModel;
import com.example.app.app.staff.domain.dto.StaffRequestModel;
import com.example.app.app.staff.domain.dto.StaffResponseModel;
import com.example.app.app.staff.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/staffs")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    private final StaffRepresentationModelAssembler staffAssembler;
    private final StaffDetailsRepresentationModelAssembler staffDetailsAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<StaffResponseModel>> getStaffList() {
        return ResponseEntity.ok(staffAssembler.toCollectionModel(
                staffService.getStaffList()));
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> addStaff(@RequestBody StaffRequestModel model) {
        var result = staffService.addStaff(model);
        return ResponseEntity.created(linkTo(methodOn(StaffController.class)
                .getStaff(String.valueOf(result.getStaffId()))).toUri()).build();
    }

    @GetMapping(path = "/{staffId}")
    public ResponseEntity<StaffResponseModel> getStaff(@PathVariable String staffId) {
        return staffService.getStaff(staffId)
                .map(staffAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{staffId}")
    public ResponseEntity<Void> updateStaff(@PathVariable String staffId, @RequestBody StaffRequestModel model) {
        var result = staffService.updateStaff(staffId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{staffId}")
    public ResponseEntity<Void> deleteStaff(@PathVariable String staffId) {
        staffService.removeStaff(staffId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{staffId}/details")
    public ResponseEntity<StaffDetailResponseModel> getStaffDetails(@PathVariable String staffId) {
        return staffService.getStaffDetails(staffId)
                .map(staffDetailsAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
