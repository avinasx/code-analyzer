package com.example.app.hateoas.assembler;

import com.example.app.controller.PaymentController;
import com.example.app.model.internal.StoreSalesModel;
import com.example.app.model.response.StoreSalesResponseModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
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

    @NotNull
    @Override
    public StoreSalesResponseModel toModel(@NotNull StoreSalesModel entity) {
        var model = new StoreSalesResponseModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }

    @NotNull
    @Override
    public CollectionModel<StoreSalesResponseModel> toCollectionModel(@NotNull Iterable<? extends StoreSalesModel> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(PaymentController.class).getSalesByCategory()).withRel("categorySales"));
        collectionModel.add(linkTo(methodOn(PaymentController.class).getSalesByStore()).withRel("storeSales"));
        return collectionModel;
    }
}
