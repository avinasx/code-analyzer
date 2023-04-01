package com.example.app.hateoas.assembler;

import com.example.app.controller.StaffController;
import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.extra.StaffDetailsModel;
import com.example.app.model.response.StaffDetailResponseModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StaffDetailsRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StaffDetailsModel, StaffDetailResponseModel> {
    public StaffDetailsRepresentationModelAssembler() {
        super(StaffController.class, StaffDetailResponseModel.class);
    }

    @Override
    @lombok.NonNull
    public StaffDetailResponseModel toModel(@lombok.NonNull StaffDetailsModel entity) {
        var model = instantiateModel(entity);
        model.setStaffDetailsModel(entity);
        model.add(linkTo(methodOn(StaffController.class).getStaffDetails(String.valueOf(entity.getId()))).withSelfRel());
        model.add(linkTo(methodOn(StaffController.class).getStaff(String.valueOf(entity.getId()))).withRel(HalRelation.Fields.staff));
        model.add(linkTo(methodOn(StaffController.class).getStaffList()).withRel(HalRelation.Fields.staffList));
        return model;
    }
}
