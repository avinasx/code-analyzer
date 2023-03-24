package com.example.app.hateoas.assembler;

import com.example.app.controller.PaymentController;
import com.example.app.model.internal.StoreSalesModel;
import com.example.app.model.response.StoreSalesResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreSalesRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StoreSalesModel, StoreSalesResponseModel> {
    public StoreSalesRepresentationModelAssembler() {
        super(StoreSalesModel.class, StoreSalesResponseModel.class);
    }

    @Override
    @NonNull
    public StoreSalesResponseModel toModel(@NonNull StoreSalesModel entity) {
        var model = instantiateModel(entity);
        model.setStoreSalesModel(entity);
        return model;
    }

    @NonNull
    @Override
    public CollectionModel<StoreSalesResponseModel> toCollectionModel(@NonNull Iterable<? extends StoreSalesModel> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(PaymentController.class).getSalesByStore()).withSelfRel());
        collectionModel.add(linkTo(methodOn(PaymentController.class).getSalesByCategory()).withRel("categorySales"));
        return collectionModel;
    }
}
