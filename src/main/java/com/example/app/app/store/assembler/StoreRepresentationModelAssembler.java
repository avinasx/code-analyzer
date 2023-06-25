package com.example.app.app.store.assembler;

import com.example.app.app.store.controller.StoreController;
import com.example.app.app.store.domain.dto.StoreDto;
import com.example.app.common.constant.HalRelation;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StoreDto.Store, StoreDto.StoreResponse> {
    public StoreRepresentationModelAssembler() {
        super(StoreController.class, StoreDto.StoreResponse.class);
    }

    @Override
    @lombok.NonNull
    public StoreDto.StoreResponse toModel(@lombok.NonNull StoreDto.Store entity) {
        var model = instantiateModel(entity);
        model.setStore(entity);
        model.add(linkTo(methodOn(StoreController.class).getStore(entity.getStoreId())).withSelfRel());
        model.add(linkTo(methodOn(StoreController.class).getStoreList(Pageable.unpaged())).withRel(HalRelation.Fields.storeList));
        return model;
    }
}
