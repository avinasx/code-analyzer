package com.example.app.hateoas.assembler;

import com.example.app.controller.RentalController;
import com.example.app.model.internal.RentalModel;
import com.example.app.model.response.RentalResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RentalRepresentationModelAssembler extends RepresentationModelAssemblerSupport<RentalModel, RentalResponseModel> {
    public RentalRepresentationModelAssembler() {
        super(RentalController.class, RentalResponseModel.class);
    }

    @NonNull
    @Override
    public RentalResponseModel toModel(@NonNull RentalModel entity) {
        var model = instantiateModel(entity);
        model.setRentalModel(entity);
        model.add(linkTo(methodOn(RentalController.class).getRental(String.valueOf(entity.getRentalId()))).withSelfRel());
        model.add(linkTo(methodOn(RentalController.class).getAllRentals()).withRel("rentals"));
        return model;
    }
}
