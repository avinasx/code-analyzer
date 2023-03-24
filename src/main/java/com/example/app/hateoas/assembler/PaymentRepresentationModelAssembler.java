package com.example.app.hateoas.assembler;

import com.example.app.controller.PaymentController;
import com.example.app.model.internal.PaymentModel;
import com.example.app.model.response.PaymentResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentRepresentationModelAssembler extends RepresentationModelAssemblerSupport<PaymentModel, PaymentResponseModel> {
    public PaymentRepresentationModelAssembler() {
        super(PaymentController.class, PaymentResponseModel.class);
    }

    @Override
    @NonNull
    public PaymentResponseModel toModel(@NonNull PaymentModel entity) {
        var model = instantiateModel(entity);
        model.setPaymentModel(entity);
        model.add(linkTo(methodOn(PaymentController.class).getPayment(String.valueOf(entity.getPaymentId()))).withSelfRel());
        model.add(linkTo(methodOn(PaymentController.class).getAllPayments()).withRel("payments"));
        return model;
    }
}
