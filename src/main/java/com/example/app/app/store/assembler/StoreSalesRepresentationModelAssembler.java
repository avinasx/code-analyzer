package com.example.app.app.store.assembler;

import com.example.app.app.store.controller.ReportController;
import com.example.app.app.store.domain.dto.StoreSalesDto;
import com.example.app.common.constant.HalRelation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreSalesRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StoreSalesDto.StoreSales, StoreSalesDto.StoreSalesResponse> {
    public StoreSalesRepresentationModelAssembler() {
        super(ReportController.class, StoreSalesDto.StoreSalesResponse.class);
    }

    @Override
    @lombok.NonNull
    public StoreSalesDto.StoreSalesResponse toModel(@lombok.NonNull StoreSalesDto.StoreSales entity) {
        var model = instantiateModel(entity);
        model.setStoreSales(entity);
        return model;
    }

    @Override
    @lombok.NonNull
    public CollectionModel<StoreSalesDto.StoreSalesResponse> toCollectionModel(@lombok.NonNull Iterable<? extends StoreSalesDto.StoreSales> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(ReportController.class).reportSalesByStore()).withSelfRel());
        collectionModel.add(linkTo(methodOn(ReportController.class).reportSalesByCategory()).withRel(HalRelation.Fields.categorySales));
        return collectionModel;
    }
}
