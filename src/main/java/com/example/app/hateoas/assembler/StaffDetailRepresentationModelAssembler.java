package com.example.app.hateoas.assembler;

import com.example.app.controller.StaffController;
import com.example.app.model.internal.StaffDetailModel;
import com.example.app.model.response.StaffDetailResponseModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StaffDetailRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StaffDetailModel, StaffDetailResponseModel> {
    public StaffDetailRepresentationModelAssembler() {
        super(StaffController.class, StaffDetailResponseModel.class);
    }

    @NotNull
    @Override
    public StaffDetailResponseModel toModel(@NotNull StaffDetailModel entity) {
        var model = new StaffDetailResponseModel();
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(StaffController.class).getStaffDetail(String.valueOf(model.getId()))).withSelfRel());
        model.add(linkTo(methodOn(StaffController.class).getStaff(String.valueOf(model.getId()))).withRel("staff"));
        model.add(linkTo(methodOn(StaffController.class).getAllStaffs()).withRel("staffs"));
        return model;
    }
}
