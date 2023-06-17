package com.example.app.app.location.assembler;

import com.example.app.app.location.controller.LocationController;
import com.example.app.app.location.domain.dto.AddressDto;
import com.example.app.common.constant.HalRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AddressRepresentationModelAssembler extends RepresentationModelAssemblerSupport<AddressDto.Address, AddressDto.AddressResponse> {
    public AddressRepresentationModelAssembler() {
        super(LocationController.class, AddressDto.AddressResponse.class);
    }

    @Override
    @lombok.NonNull
    public AddressDto.AddressResponse toModel(@lombok.NonNull AddressDto.Address entity) {
        var model = instantiateModel(entity);
        model.setAddress(entity);
        model.add(linkTo(methodOn(LocationController.class).getAddress(String.valueOf(entity.getAddressId()))).withSelfRel());
        model.add(linkTo(methodOn(LocationController.class).getAddressList()).withRel(HalRelation.Fields.addressList));
        return model;
    }
}
