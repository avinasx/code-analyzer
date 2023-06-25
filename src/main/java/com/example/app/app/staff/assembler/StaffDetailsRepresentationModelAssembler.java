package com.example.app.app.staff.assembler;

import com.example.app.app.staff.controller.StaffController;
import com.example.app.app.staff.domain.dto.StaffDetailsDto;
import com.example.app.common.constant.HalRelation;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StaffDetailsRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StaffDetailsDto.StaffDetails, StaffDetailsDto.StaffDetailsResponse> {
    public StaffDetailsRepresentationModelAssembler() {
        super(StaffController.class, StaffDetailsDto.StaffDetailsResponse.class);
    }

    @Override
    @lombok.NonNull
    public StaffDetailsDto.StaffDetailsResponse toModel(@lombok.NonNull StaffDetailsDto.StaffDetails entity) {
        var model = instantiateModel(entity);
        model.setStaffDetails(entity);
        model.add(linkTo(methodOn(StaffController.class).getStaffDetails(entity.getId())).withSelfRel());
        model.add(linkTo(methodOn(StaffController.class).getStaff(entity.getId())).withRel(HalRelation.Fields.staff));
        model.add(linkTo(methodOn(StaffController.class).getStaffList(Pageable.unpaged())).withRel(HalRelation.Fields.staffList));
        return model;
    }
}
