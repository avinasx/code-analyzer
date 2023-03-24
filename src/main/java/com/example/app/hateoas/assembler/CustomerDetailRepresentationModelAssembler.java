package com.example.app.hateoas.assembler;

import com.example.app.controller.CustomerController;
import com.example.app.model.internal.CustomerDetailModel;
import com.example.app.model.response.CustomerDetailResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerDetailRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CustomerDetailModel, CustomerDetailResponseModel> {
    public CustomerDetailRepresentationModelAssembler() {
        super(CustomerDetailModel.class, CustomerDetailResponseModel.class);
    }

    @Override
    @NonNull
    public CustomerDetailResponseModel toModel(@NonNull CustomerDetailModel entity) {
        var model = instantiateModel(entity);
        model.setCustomerDetailModel(entity);
        model.add(linkTo(methodOn(CustomerController.class).getCustomerDetail(String.valueOf(entity.getId()))).withSelfRel());
        model.add(linkTo(methodOn(CustomerController.class).getCustomer(String.valueOf(entity.getId()))).withRel("customer"));
        model.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
        return model;
    }
}
