package com.example.app.app.location.assembler;

import com.example.app.app.catalog.domain.dto.AddressModel;
import com.example.app.app.location.controller.LocationController;
import com.example.app.app.location.domain.dto.AddressResponseModel;
import com.example.app.common.constant.HalRelation;
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
