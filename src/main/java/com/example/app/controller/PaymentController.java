package com.example.app.controller;

import com.example.app.hateoas.assembler.CategorySalesRepresentationModelAssembler;
import com.example.app.hateoas.assembler.PaymentRepresentationModelAssembler;
import com.example.app.hateoas.assembler.StoreSalesRepresentationModelAssembler;
import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.response.CategorySalesResponseModel;
import com.example.app.model.response.PaymentResponseModel;
import com.example.app.model.response.StoreSalesResponseModel;
import com.example.app.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@AllArgsConstructor
public class PaymentController {
    private PaymentService paymentService;
    private PaymentRepresentationModelAssembler paymentAssembler;
    private CategorySalesRepresentationModelAssembler categorySalesAssembler;
    private StoreSalesRepresentationModelAssembler storeSalesAssembler;

    private static Links modifyAndRearrangeLinks(CollectionModel<?> collectionModel, String relation) {
        var selfLink = Objects.requireNonNull(collectionModel.getLink(LinkRelation.of(relation)).orElse(null))
                .withRel(IanaLinkRelations.SELF);
        var otherLinks = collectionModel.getLinks().stream()
                .filter(link -> !link.getRel().equals(LinkRelation.of(relation)))
                .toArray(Link[]::new);
        return Links.of(selfLink).and(otherLinks);
    }

    @GetMapping(path = "/payments")
    public ResponseEntity<CollectionModel<PaymentResponseModel>> getAllPayments() {
        return ResponseEntity.ok(paymentAssembler.toCollectionModel(paymentService.getAllPayments()));
    }

    @PostMapping(path = "/payments")
    public ResponseEntity<Void> addPayment(@ModelAttribute PaymentEntity entity) {
        paymentService.addPayment(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/payments/{id}")
    public ResponseEntity<PaymentResponseModel> getPayment(@PathVariable String id) {
        return paymentService.getPaymentById(Integer.valueOf(id))
                .map(paymentAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/payments/{id}")
    public ResponseEntity<Void> updatePayment(@PathVariable String id, @ModelAttribute PaymentEntity entity) {
        paymentService.updatePayment(entity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/payments/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable String id) {
        paymentService.removePaymentById(Integer.valueOf(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/payments/{id}/details")
    public ResponseEntity<PaymentResponseModel> getPaymentDetail(@PathVariable String id) {
        return paymentService.getPaymentDetailById(Integer.valueOf(id))
                .map(paymentAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/sales/categories")
    public ResponseEntity<CollectionModel<CategorySalesResponseModel>> getSalesByCategory() {
        var collectionModel = categorySalesAssembler.toCollectionModel(paymentService.getSalesByCategory());
        var modifiedLinks = modifyAndRearrangeLinks(collectionModel, "categorySales");
        return ResponseEntity.ok(CollectionModel.of(collectionModel.getContent(), modifiedLinks));
    }

    @GetMapping(path = "/sales/stores")
    public ResponseEntity<CollectionModel<StoreSalesResponseModel>> getSalesByStore() {
        var collectionModel = storeSalesAssembler.toCollectionModel(paymentService.getSalesByStore());
        var modifiedLinks = modifyAndRearrangeLinks(collectionModel, "storeSales");
        return ResponseEntity.ok(CollectionModel.of(collectionModel.getContent(), modifiedLinks));
    }
}
