package com.example.app.hateoas.assembler;

import com.example.app.controller.StaffController;
import com.example.app.model.internal.extra.StaffDetailModel;
import com.example.app.model.response.StaffDetailResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StaffDetailRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StaffDetailModel, StaffDetailResponseModel> {
    public StaffDetailRepresentationModelAssembler() {
        super(StaffController.class, StaffDetailResponseModel.class);
    }

    @Override
    @NonNull
    public StaffDetailResponseModel toModel(@NonNull StaffDetailModel entity) {
        var model = instantiateModel(entity);
        model.setStaffDetailModel(entity);
        model.add(linkTo(methodOn(StaffController.class).getStaffDetail(String.valueOf(entity.getId()))).withSelfRel());
        model.add(linkTo(methodOn(StaffController.class).getStaff(String.valueOf(entity.getId()))).withRel("staff"));
        model.add(linkTo(methodOn(StaffController.class).getStaffs()).withRel("staffs"));
        return model;
    }
}
