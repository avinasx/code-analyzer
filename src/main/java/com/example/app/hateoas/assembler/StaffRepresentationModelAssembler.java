package com.example.app.hateoas.assembler;

import com.example.app.controller.StaffController;
import com.example.app.model.internal.StaffModel;
import com.example.app.model.response.StaffResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StaffRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StaffModel, StaffResponseModel> {
    public StaffRepresentationModelAssembler() {
        super(StaffController.class, StaffResponseModel.class);
    }

    @NonNull
    @Override
    public StaffResponseModel toModel(@NonNull StaffModel entity) {
        var model = instantiateModel(entity);
        model.setStaffModel(entity);
        model.add(linkTo(methodOn(StaffController.class).getStaff(String.valueOf(entity.getStaffId()))).withSelfRel());
        model.add(linkTo(methodOn(StaffController.class).getAllStaffs()).withRel("staffs"));
        return model;
    }
}
