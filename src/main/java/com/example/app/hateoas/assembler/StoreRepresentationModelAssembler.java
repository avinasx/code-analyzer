package com.example.app.hateoas.assembler;

import com.example.app.controller.StoreController;
import com.example.app.model.entity.StoreEntity;
import com.example.app.model.response.StoreResponseModel;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StoreEntity, StoreResponseModel> {
    public StoreRepresentationModelAssembler() {
        super(StoreController.class, StoreResponseModel.class);
    }

    @NotNull
    @Override
    public StoreResponseModel toModel(@NonNull StoreEntity entity) {
        var model = instantiateModel(entity);
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(StoreController.class).getStore(String.valueOf(model.getStoreId()))).withSelfRel());
        model.add(linkTo(methodOn(StoreController.class).getAllStores()).withRel("stores"));
        return model;
    }
}
