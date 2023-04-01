package com.example.app.hateoas.assembler;

import com.example.app.controller.LocationController;
import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.core.AddressModel;
import com.example.app.model.response.AddressResponseModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AddressRepresentationModelAssembler extends RepresentationModelAssemblerSupport<AddressModel, AddressResponseModel> {
    public AddressRepresentationModelAssembler() {
        super(LocationController.class, AddressResponseModel.class);
    }

    @Override
    @lombok.NonNull
    public AddressResponseModel toModel(@lombok.NonNull AddressModel entity) {
        var model = instantiateModel(entity);
        model.setAddressModel(entity);
        model.add(linkTo(methodOn(LocationController.class).getAddress(String.valueOf(entity.getAddressId()))).withSelfRel());
        model.add(linkTo(methodOn(LocationController.class).getAddressList()).withRel(HalRelation.Fields.addressList));
        return model;
    }
}
