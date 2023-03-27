package com.example.app.controller;

import com.example.app.hateoas.assembler.PaymentRepresentationModelAssembler;
import com.example.app.model.request.PaymentRequestModel;
import com.example.app.model.response.PaymentResponseModel;
import com.example.app.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/payments")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentRepresentationModelAssembler paymentAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<PaymentResponseModel>> getPayments() {
        return ResponseEntity.ok(paymentAssembler.toCollectionModel(
                paymentService.getPayments()));
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
    public ResponseEntity<PaymentResponseModel> getPaymentDetail(@PathVariable String paymentId) {
        return paymentService.getPaymentDetail(paymentId)
                .map(paymentAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
