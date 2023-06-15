package com.example.app.app.staff.assembler;

import com.example.app.app.staff.controller.StaffController;
import com.example.app.app.staff.domain.dto.StaffModel;
import com.example.app.app.staff.domain.dto.StaffResponseModel;
import com.example.app.common.constant.HalRelation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StaffRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StaffModel, StaffResponseModel> {
    public StaffRepresentationModelAssembler() {
        super(StaffController.class, StaffResponseModel.class);
    }

    @Override
    @lombok.NonNull
    public StaffResponseModel toModel(@lombok.NonNull StaffModel entity) {
        var model = instantiateModel(entity);
        model.setStaffModel(entity);
        model.add(linkTo(methodOn(StaffController.class).getStaff(String.valueOf(entity.getStaffId()))).withSelfRel());
        model.add(linkTo(methodOn(StaffController.class).getStaffList()).withRel(HalRelation.Fields.staffList));
        return model;
    }

    @Override
    @lombok.NonNull
    public CollectionModel<StaffResponseModel> toCollectionModel(@lombok.NonNull Iterable<? extends StaffModel> entities) {
        return super.toCollectionModel(entities);
    }
}
