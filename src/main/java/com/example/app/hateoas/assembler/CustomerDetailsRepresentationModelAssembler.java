package com.example.app.hateoas.assembler;

import com.example.app.controller.CustomerController;
import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.extra.CustomerDetailsModel;
import com.example.app.model.response.CustomerDetailResponseModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerDetailsRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CustomerDetailsModel, CustomerDetailResponseModel> {
    public CustomerDetailsRepresentationModelAssembler() {
        super(CustomerDetailsModel.class, CustomerDetailResponseModel.class);
    }

    @Override
    @lombok.NonNull
    public CustomerDetailResponseModel toModel(@lombok.NonNull CustomerDetailsModel entity) {
        var model = instantiateModel(entity);
        model.setCustomerDetailsModel(entity);
        model.add(linkTo(methodOn(CustomerController.class).getCustomerDetails(String.valueOf(entity.getId()))).withSelfRel());
        model.add(linkTo(methodOn(CustomerController.class).getCustomer(String.valueOf(entity.getId()))).withRel(HalRelation.Fields.customer));
        model.add(linkTo(methodOn(CustomerController.class).getCustomerList()).withRel(HalRelation.Fields.customerList));
        return model;
    }
}
