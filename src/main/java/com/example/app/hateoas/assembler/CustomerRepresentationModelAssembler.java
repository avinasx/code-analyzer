package com.example.app.hateoas.assembler;

import com.example.app.controller.CustomerController;
import com.example.app.model.internal.core.CustomerModel;
import com.example.app.model.response.CustomerResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CustomerModel, CustomerResponseModel> {
    public CustomerRepresentationModelAssembler() {
        super(CustomerController.class, CustomerResponseModel.class);
    }

    @Override
    @NonNull
    public CustomerResponseModel toModel(@NonNull CustomerModel entity) {
        var model = instantiateModel(entity);
        model.setCustomerModel(entity);
        model.add(linkTo(methodOn(CustomerController.class).getCustomer(String.valueOf(entity.getCustomerId()))).withSelfRel());
        model.add(linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));
        return model;
    }
}
