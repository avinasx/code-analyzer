package com.example.app.controller;

import com.example.app.hateoas.assembler.StaffDetailsRepresentationModelAssembler;
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
@RequestMapping(value = "/staffs")
@AllArgsConstructor
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
