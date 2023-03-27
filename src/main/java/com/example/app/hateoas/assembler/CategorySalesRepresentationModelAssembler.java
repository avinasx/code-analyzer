package com.example.app.hateoas.assembler;

import com.example.app.controller.ReportController;
import com.example.app.model.internal.extra.CategorySalesModel;
import com.example.app.model.response.CategorySalesResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategorySalesRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CategorySalesModel, CategorySalesResponseModel> {
    public CategorySalesRepresentationModelAssembler() {
        super(ReportController.class, CategorySalesResponseModel.class);
    }

    @Override
    @NonNull
    public CategorySalesResponseModel toModel(@NonNull CategorySalesModel entity) {
        var model = instantiateModel(entity);
        model.setCategorySalesModel(entity);
        return model;
    }

    @Override
    @NonNull
    public CollectionModel<CategorySalesResponseModel> toCollectionModel(@NonNull Iterable<? extends CategorySalesModel> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(ReportController.class).reportSalesByCategory()).withSelfRel());
        collectionModel.add(linkTo(methodOn(ReportController.class).reportSalesByStore()).withRel("storeSales"));
        return collectionModel;
    }
}
