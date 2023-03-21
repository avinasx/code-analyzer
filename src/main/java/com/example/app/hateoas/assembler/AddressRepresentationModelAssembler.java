package com.example.app.hateoas.assembler;

import com.example.app.controller.LocationController;
import com.example.app.model.internal.AddressModel;
import com.example.app.model.response.AddressResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AddressRepresentationModelAssembler extends RepresentationModelAssemblerSupport<AddressModel, AddressResponseModel> {
    public AddressRepresentationModelAssembler() {
        super(LocationController.class, AddressResponseModel.class);
    }

    @NonNull
    @Override
    public AddressResponseModel toModel(@NonNull AddressModel entity) {
        var model = instantiateModel(entity);
        model.setAddressModel(entity);
        model.add(linkTo(methodOn(LocationController.class).getAddress(String.valueOf(entity.getAddressId()))).withSelfRel());
        model.add(linkTo(methodOn(LocationController.class).getAllAddresses()).withRel("addresses"));
        return model;
    }
}
