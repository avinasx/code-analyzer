package com.example.app.hateoas.assembler;

import com.example.app.controller.LocationController;
import com.example.app.model.entity.CityEntity;
import com.example.app.model.response.CityResponseModel;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CityRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CityEntity, CityResponseModel> {
    public CityRepresentationModelAssembler() {
        super(LocationController.class, CityResponseModel.class);
    }

    @NotNull
    @Override
    public CityResponseModel toModel(@NonNull CityEntity entity) {
        var model = new CityResponseModel();
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(LocationController.class).getCity(String.valueOf(model.getCityId()))).withSelfRel());
        model.add(linkTo(methodOn(LocationController.class).getAllAddresses()).withRel("cities"));
        return model;
    }
}
