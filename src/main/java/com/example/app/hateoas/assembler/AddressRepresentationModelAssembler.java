package com.example.app.hateoas.assembler;

import com.example.app.controller.LocationController;
import com.example.app.model.entity.AddressEntity;
import com.example.app.model.response.AddressResponseModel;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AddressRepresentationModelAssembler extends RepresentationModelAssemblerSupport<AddressEntity, AddressResponseModel> {
    public AddressRepresentationModelAssembler() {
        super(LocationController.class, AddressResponseModel.class);
    }

    @NonNull
    @Override
    public AddressResponseModel toModel(@NonNull AddressEntity entity) {
        var model = instantiateModel(entity);
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(LocationController.class).getAddress(String.valueOf(model.getAddressId()))).withSelfRel());
        model.add(linkTo(methodOn(LocationController.class).getAllAddresses()).withRel("addresses"));
        return model;
    }
}
