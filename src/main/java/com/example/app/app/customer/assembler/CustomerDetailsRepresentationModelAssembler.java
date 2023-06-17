package com.example.app.app.customer.assembler;

import com.example.app.app.customer.controller.CustomerController;
import com.example.app.app.customer.domain.dto.CustomerDetailsDto;
import com.example.app.common.constant.HalRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerDetailsRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CustomerDetailsDto.CustomerDetails, CustomerDetailsDto.CustomerDetailsResponse> {
    public CustomerDetailsRepresentationModelAssembler() {
        super(CustomerDetailsDto.CustomerDetails.class, CustomerDetailsDto.CustomerDetailsResponse.class);
    }

    @Override
    @lombok.NonNull
    public CustomerDetailsDto.CustomerDetailsResponse toModel(@lombok.NonNull CustomerDetailsDto.CustomerDetails entity) {
        var model = instantiateModel(entity);
        model.setCustomerDetailsModel(entity);
        model.add(linkTo(methodOn(CustomerController.class).getCustomerDetails(String.valueOf(entity.getId()))).withSelfRel());
        model.add(linkTo(methodOn(CustomerController.class).getCustomer(String.valueOf(entity.getId()))).withRel(HalRelation.Fields.customer));
        model.add(linkTo(methodOn(CustomerController.class).getCustomerList()).withRel(HalRelation.Fields.customerList));
        return model;
    }
}
