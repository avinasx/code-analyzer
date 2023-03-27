package com.example.app.hateoas.assembler;

import com.example.app.controller.StoreController;
import com.example.app.model.internal.extra.StoreDetailModel;
import com.example.app.model.response.StoreDetailResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreDetailRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StoreDetailModel, StoreDetailResponseModel> {
    public StoreDetailRepresentationModelAssembler() {
        super(StoreController.class, StoreDetailResponseModel.class);
    }

    @Override
    @NonNull
    public StoreDetailResponseModel toModel(@NonNull StoreDetailModel entity) {
        var model = instantiateModel(entity);
        model.setStoreDetailModel(entity);
        model.add(linkTo(methodOn(StoreController.class).getStoreDetail(String.valueOf(entity.getId()))).withSelfRel());
        model.add(linkTo(methodOn(StoreController.class).getStore(String.valueOf(entity.getId()))).withRel("store"));
        model.add(linkTo(methodOn(StoreController.class).getStores()).withRel("stores"));
        return model;
    }
}
