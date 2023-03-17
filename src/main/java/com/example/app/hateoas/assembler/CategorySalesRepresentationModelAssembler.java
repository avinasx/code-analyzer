package com.example.app.hateoas.assembler;

import com.example.app.controller.PaymentController;
import com.example.app.model.internal.CategorySalesModel;
import com.example.app.model.response.CategorySalesResponseModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategorySalesRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CategorySalesModel, CategorySalesResponseModel> {
    public CategorySalesRepresentationModelAssembler() {
        super(CategorySalesModel.class, CategorySalesResponseModel.class);
    }

    @NotNull
    @Override
    public CategorySalesResponseModel toModel(@NotNull CategorySalesModel entity) {
        var model = new CategorySalesResponseModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }

    @NotNull
    @Override
    public CollectionModel<CategorySalesResponseModel> toCollectionModel(@NotNull Iterable<? extends CategorySalesModel> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(PaymentController.class).getSalesByCategory()).withSelfRel());
        collectionModel.add(linkTo(methodOn(PaymentController.class).getSalesByStore()).withRel("storeSales"));
        return collectionModel;
    }
}
