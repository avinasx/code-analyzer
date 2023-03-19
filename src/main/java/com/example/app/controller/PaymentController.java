package com.example.app.controller;

import com.example.app.hateoas.assembler.CategorySalesRepresentationModelAssembler;
import com.example.app.hateoas.assembler.PaymentRepresentationModelAssembler;
import com.example.app.hateoas.assembler.StoreSalesRepresentationModelAssembler;
import com.example.app.model.request.PaymentRequestModel;
import com.example.app.model.response.CategorySalesResponseModel;
import com.example.app.model.response.PaymentResponseModel;
import com.example.app.model.response.StoreSalesResponseModel;
import com.example.app.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentRepresentationModelAssembler paymentAssembler;
    private final CategorySalesRepresentationModelAssembler categorySalesAssembler;
    private final StoreSalesRepresentationModelAssembler storeSalesAssembler;

    @GetMapping(path = "/payments")
    public ResponseEntity<CollectionModel<PaymentResponseModel>> getAllPayments() {
        return ResponseEntity.ok(paymentAssembler.toCollectionModel(paymentService.getAllPayments()));
    }

    @PostMapping(path = "/payments")
    public ResponseEntity<Void> addPayment(@RequestBody PaymentRequestModel model) {
        var result = paymentService.addPayment(model);
        return ResponseEntity.created(linkTo(methodOn(PaymentController.class)
                .getPayment(String.valueOf(result.getPaymentId()))).toUri()).build();
    }

    @GetMapping(path = "/payments/{id}")
    public ResponseEntity<PaymentResponseModel> getPayment(@PathVariable String id) {
        return paymentService.getPaymentById(id)
                .map(paymentAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/payments/{id}")
    public ResponseEntity<Void> updatePayment(@PathVariable String id, @RequestBody PaymentRequestModel model) {
        var result = paymentService.updatePayment(id, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/payments/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable String id) {
        paymentService.removePaymentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/payments/{id}/details")
    public ResponseEntity<PaymentResponseModel> getPaymentDetail(@PathVariable String id) {
        return paymentService.getPaymentDetailById(id)
                .map(paymentAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/sales/categories")
    public ResponseEntity<CollectionModel<CategorySalesResponseModel>> getSalesByCategory() {
        return ResponseEntity.ok(categorySalesAssembler.toCollectionModel(paymentService.getSalesByCategory()));
    }

    @GetMapping(path = "/sales/stores")
    public ResponseEntity<CollectionModel<StoreSalesResponseModel>> getSalesByStore() {
        return ResponseEntity.ok(storeSalesAssembler.toCollectionModel(paymentService.getSalesByStore()));
    }
}
