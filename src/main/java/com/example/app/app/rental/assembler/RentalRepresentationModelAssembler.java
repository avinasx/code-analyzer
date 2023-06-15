package com.example.app.app.rental.assembler;

import com.example.app.app.rental.controller.RentalController;
import com.example.app.app.rental.domain.dto.RentalModel;
import com.example.app.app.rental.domain.dto.RentalResponseModel;
import com.example.app.common.constant.HalRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RentalRepresentationModelAssembler extends RepresentationModelAssemblerSupport<RentalModel, RentalResponseModel> {
    public RentalRepresentationModelAssembler() {
        super(RentalController.class, RentalResponseModel.class);
    }

    @Override
    @lombok.NonNull
    public RentalResponseModel toModel(@lombok.NonNull RentalModel entity) {
        var model = instantiateModel(entity);
        model.setRentalModel(entity);
        model.add(linkTo(methodOn(RentalController.class).getRental(String.valueOf(entity.getRentalId()))).withSelfRel());
        model.add(linkTo(methodOn(RentalController.class).getRentalList()).withRel(HalRelation.Fields.rentalList));
        return model;
    }
}
