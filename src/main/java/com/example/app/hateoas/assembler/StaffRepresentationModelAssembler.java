package com.example.app.hateoas.assembler;

import com.example.app.controller.StaffController;
import com.example.app.model.entity.StaffEntity;
import com.example.app.model.response.StaffResponseModel;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StaffRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StaffEntity, StaffResponseModel> {
    public StaffRepresentationModelAssembler() {
        super(StaffController.class, StaffResponseModel.class);
    }

    @NotNull
    @Override
    public StaffResponseModel toModel(@NonNull StaffEntity entity) {
        var model = new StaffResponseModel();
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(StaffController.class).getStaff(String.valueOf(model.getStaffId()))).withSelfRel());
        model.add(linkTo(methodOn(StaffController.class).getAllStaffs()).withRel("staffs"));
        return model;
    }
}
