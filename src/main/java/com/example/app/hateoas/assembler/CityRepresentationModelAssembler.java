package com.example.app.hateoas.assembler;

import com.example.app.controller.LocationController;
import com.example.app.model.internal.CityModel;
import com.example.app.model.response.CityResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CityRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CityModel, CityResponseModel> {
    public CityRepresentationModelAssembler() {
        super(LocationController.class, CityResponseModel.class);
    }

    @Override
    @NonNull
    public CityResponseModel toModel(@NonNull CityModel entity) {
        var model = instantiateModel(entity);
        model.setCityModel(entity);
        model.add(linkTo(methodOn(LocationController.class).getCity(String.valueOf(entity.getCityId()))).withSelfRel());
        model.add(linkTo(methodOn(LocationController.class).getAllAddresses()).withRel("cities"));
        return model;
    }
}
