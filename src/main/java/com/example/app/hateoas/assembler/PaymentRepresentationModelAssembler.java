package com.example.app.hateoas.assembler;

import com.example.app.controller.PaymentController;
import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.response.PaymentResponseModel;
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
    @lombok.NonNull
    public PaymentResponseModel toModel(@lombok.NonNull PaymentModel entity) {
        var model = instantiateModel(entity);
        model.setPaymentModel(entity);
        model.add(linkTo(methodOn(PaymentController.class).getPayment(String.valueOf(entity.getPaymentId()))).withSelfRel());
        model.add(linkTo(methodOn(PaymentController.class).getPaymentList()).withRel(HalRelation.Fields.paymentList));
        return model;
    }
}
