package com.example.app.hateoas.assembler;

import com.example.app.controller.ReportController;
import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.extra.StoreSalesModel;
import com.example.app.model.response.StoreSalesResponseModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreSalesRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StoreSalesModel, StoreSalesResponseModel> {
    public StoreSalesRepresentationModelAssembler() {
        super(ReportController.class, StoreSalesResponseModel.class);
    }

    @Override
    @lombok.NonNull
    public StoreSalesResponseModel toModel(@lombok.NonNull StoreSalesModel entity) {
        var model = instantiateModel(entity);
        model.setStoreSalesModel(entity);
        return model;
    }

    @Override
    @lombok.NonNull
    public CollectionModel<StoreSalesResponseModel> toCollectionModel(@lombok.NonNull Iterable<? extends StoreSalesModel> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(ReportController.class).reportSalesByStore()).withSelfRel());
        collectionModel.add(linkTo(methodOn(ReportController.class).reportSalesByCategory()).withRel(HalRelation.Fields.categorySales));
        return collectionModel;
    }
}
