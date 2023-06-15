package com.example.app.app.store.assembler;

import com.example.app.app.store.controller.StoreController;
import com.example.app.app.store.domain.dto.StoreModel;
import com.example.app.app.store.domain.dto.StoreResponseModel;
import com.example.app.common.constant.HalRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StoreModel, StoreResponseModel> {
    public StoreRepresentationModelAssembler() {
        super(StoreController.class, StoreResponseModel.class);
    }

    @Override
    @lombok.NonNull
    public StoreResponseModel toModel(@lombok.NonNull StoreModel entity) {
        var model = instantiateModel(entity);
        model.setStoreModel(entity);
        model.add(linkTo(methodOn(StoreController.class).getStore(String.valueOf(entity.getStoreId()))).withSelfRel());
        model.add(linkTo(methodOn(StoreController.class).getStoreList()).withRel(HalRelation.Fields.storeList));
        return model;
    }
}
