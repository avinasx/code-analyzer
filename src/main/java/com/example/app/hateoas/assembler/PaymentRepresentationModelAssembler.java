package com.example.app.hateoas.assembler;

import com.example.app.controller.PaymentController;
import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.response.PaymentResponseModel;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentRepresentationModelAssembler extends RepresentationModelAssemblerSupport<PaymentEntity, PaymentResponseModel> {
    public PaymentRepresentationModelAssembler() {
        super(PaymentController.class, PaymentResponseModel.class);
    }

    @NotNull
    @Override
    public PaymentResponseModel toModel(@NonNull PaymentEntity entity) {
        var model = new PaymentResponseModel();
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(PaymentController.class).getPayment(String.valueOf(model.getPaymentId()))).withSelfRel());
        model.add(linkTo(methodOn(PaymentController.class).getAllPayments()).withRel("payments"));
        return model;
    }
}
