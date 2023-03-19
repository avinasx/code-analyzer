package com.example.app.hateoas.assembler;

import com.example.app.controller.RentalController;
import com.example.app.model.entity.RentalEntity;
import com.example.app.model.response.RentalResponseModel;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RentalRepresentationModelAssembler extends RepresentationModelAssemblerSupport<RentalEntity, RentalResponseModel> {
    public RentalRepresentationModelAssembler() {
        super(RentalController.class, RentalResponseModel.class);
    }

    @NotNull
    @Override
    public RentalResponseModel toModel(@NonNull RentalEntity entity) {
        var model = instantiateModel(entity);
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(RentalController.class).getRental(String.valueOf(model.getRentalId()))).withSelfRel());
        model.add(linkTo(methodOn(RentalController.class).getAllRentals()).withRel("rentals"));
        return model;
    }
}
