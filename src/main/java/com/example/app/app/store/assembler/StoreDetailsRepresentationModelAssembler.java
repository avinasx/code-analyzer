package com.example.app.app.store.assembler;

import com.example.app.app.staff.domain.dto.StoreDetailsModel;
import com.example.app.app.store.controller.StoreController;
import com.example.app.app.store.domain.dto.StoreDetailResponseModel;
import com.example.app.common.constant.HalRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreDetailsRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StoreDetailsModel, StoreDetailResponseModel> {
    public StoreDetailsRepresentationModelAssembler() {
        super(StoreController.class, StoreDetailResponseModel.class);
    }

    @Override
    @lombok.NonNull
    public StoreDetailResponseModel toModel(@lombok.NonNull StoreDetailsModel entity) {
        var model = instantiateModel(entity);
        model.setStoreDetailsModel(entity);
        model.add(linkTo(methodOn(StoreController.class).getStoreDetails(String.valueOf(entity.getId()))).withSelfRel());
        model.add(linkTo(methodOn(StoreController.class).getStore(String.valueOf(entity.getId()))).withRel(HalRelation.Fields.store));
        model.add(linkTo(methodOn(StoreController.class).getStoreList()).withRel(HalRelation.Fields.storeList));
        return model;
    }
}
