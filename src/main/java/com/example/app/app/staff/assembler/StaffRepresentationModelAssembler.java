package com.example.app.app.staff.assembler;

import com.example.app.app.staff.controller.StaffController;
import com.example.app.app.staff.domain.dto.StaffDto;
import com.example.app.common.constant.HalRelation;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StaffRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StaffDto.Staff, StaffDto.StaffResponse> {
    public StaffRepresentationModelAssembler() {
        super(StaffController.class, StaffDto.StaffResponse.class);
    }

    @Override
    @lombok.NonNull
    public StaffDto.StaffResponse toModel(@lombok.NonNull StaffDto.Staff entity) {
        final var model = instantiateModel(entity);
        model.setStaff(entity);
        model.add(linkTo(methodOn(StaffController.class).getStaff(entity.getStaffId())).withSelfRel());
        model.add(linkTo(methodOn(StaffController.class).getStaffList(Pageable.unpaged())).withRel(HalRelation.Fields.staffList));
        return model;
    }

    @Override
    @lombok.NonNull
    public CollectionModel<StaffDto.StaffResponse> toCollectionModel(@lombok.NonNull Iterable<? extends StaffDto.Staff> entities) {
        return super.toCollectionModel(entities);
    }
}
