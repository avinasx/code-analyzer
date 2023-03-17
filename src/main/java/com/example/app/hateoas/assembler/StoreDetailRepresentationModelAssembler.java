package com.example.app.hateoas.assembler;

import com.example.app.controller.StoreController;
import com.example.app.model.internal.StoreDetailModel;
import com.example.app.model.response.StoreDetailResponseModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreDetailRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StoreDetailModel, StoreDetailResponseModel> {
    public StoreDetailRepresentationModelAssembler() {
        super(StoreController.class, StoreDetailResponseModel.class);
    }

    @NotNull
    @Override
    public StoreDetailResponseModel toModel(@NotNull StoreDetailModel entity) {
        var model = new StoreDetailResponseModel();
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(StoreController.class).getStoreDetail(String.valueOf(model.getId()))).withSelfRel());
        model.add(linkTo(methodOn(StoreController.class).getStore(String.valueOf(model.getId()))).withRel("store"));
        model.add(linkTo(methodOn(StoreController.class).getAllStores()).withRel("stores"));
        return model;
    }
}
