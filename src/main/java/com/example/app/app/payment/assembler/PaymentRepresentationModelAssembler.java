package com.example.app.app.payment.assembler;

import com.example.app.app.payment.controller.PaymentController;
import com.example.app.app.payment.domain.dto.PaymentModel;
import com.example.app.app.payment.domain.dto.PaymentResponseModel;
import com.example.app.common.constant.HalRelation;
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
