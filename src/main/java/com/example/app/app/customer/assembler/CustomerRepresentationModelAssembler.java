package com.example.app.app.customer.assembler;

import com.example.app.app.customer.controller.CustomerController;
import com.example.app.app.customer.domain.dto.CustomerDto;
import com.example.app.common.constant.HalRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CustomerDto.Customer, CustomerDto.CustomerResponse> {
    public CustomerRepresentationModelAssembler() {
        super(CustomerController.class, CustomerDto.CustomerResponse.class);
    }

    @Override
    @lombok.NonNull
    public CustomerDto.CustomerResponse toModel(@lombok.NonNull CustomerDto.Customer entity) {
        var model = instantiateModel(entity);
        model.setCustomer(entity);
        model.add(linkTo(methodOn(CustomerController.class).getCustomer(String.valueOf(entity.getCustomerId()))).withSelfRel());
        model.add(linkTo(methodOn(CustomerController.class).getCustomerList()).withRel(HalRelation.Fields.customerList));
        return model;
    }
}
