package com.example.app.hateoas.assembler;

import com.example.app.controller.CustomerController;
import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.response.CustomerResponseModel;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CustomerEntity, CustomerResponseModel> {
    public CustomerRepresentationModelAssembler() {
        super(CustomerController.class, CustomerResponseModel.class);
    }

    @NotNull
    @Override
    public CustomerResponseModel toModel(@NonNull CustomerEntity entity) {
        var model = new CustomerResponseModel();
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(CustomerController.class).getCustomer(String.valueOf(model.getCustomerId()))).withSelfRel());
        model.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
        return model;
    }
}
