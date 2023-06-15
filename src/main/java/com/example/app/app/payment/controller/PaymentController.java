package com.example.app.app.payment.controller;

import com.example.app.app.payment.assembler.PaymentRepresentationModelAssembler;
import com.example.app.app.payment.domain.dto.PaymentRequestModel;
import com.example.app.app.payment.domain.dto.PaymentResponseModel;
import com.example.app.app.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentRepresentationModelAssembler paymentAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<PaymentResponseModel>> getPaymentList() {
        return ResponseEntity.ok(paymentAssembler.toCollectionModel(
                paymentService.getPaymentList()));
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> addPayment(@RequestBody PaymentRequestModel model) {
        var result = paymentService.addPayment(model);
        return ResponseEntity.created(linkTo(methodOn(PaymentController.class)
                .getPayment(String.valueOf(result.getPaymentId()))).toUri()).build();
    }

    @GetMapping(path = "/{paymentId}")
    public ResponseEntity<PaymentResponseModel> getPayment(@PathVariable String paymentId) {
        return paymentService.getPayment(paymentId)
                .map(paymentAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{paymentId}")
    public ResponseEntity<Void> updatePayment(@PathVariable String paymentId, @RequestBody PaymentRequestModel model) {
        var result = paymentService.updatePayment(paymentId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable String paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{paymentId}/details")
    public ResponseEntity<PaymentResponseModel> getPaymentDetails(@PathVariable String paymentId) {
        return paymentService.getPaymentDetails(paymentId)
                .map(paymentAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
