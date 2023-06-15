package com.example.app.app.customer.controller;

import com.example.app.app.customer.assembler.CustomerDetailsRepresentationModelAssembler;
import com.example.app.app.customer.assembler.CustomerRepresentationModelAssembler;
import com.example.app.app.customer.domain.dto.CustomerDetailResponseModel;
import com.example.app.app.customer.domain.dto.CustomerRequestModel;
import com.example.app.app.customer.domain.dto.CustomerResponseModel;
import com.example.app.app.customer.service.CustomerService;
import com.example.app.app.payment.assembler.PaymentRepresentationModelAssembler;
import com.example.app.app.payment.domain.dto.PaymentResponseModel;
import com.example.app.app.rental.assembler.RentalRepresentationModelAssembler;
import com.example.app.app.rental.domain.dto.RentalResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepresentationModelAssembler customerAssembler;
    private final CustomerDetailsRepresentationModelAssembler customerDetailsAssembler;
    private final PaymentRepresentationModelAssembler paymentAssembler;
    private final RentalRepresentationModelAssembler rentalAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<CustomerResponseModel>> getCustomerList() {
        return ResponseEntity.ok(customerAssembler.toCollectionModel(
                customerService.getCustomerList()));
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> addCustomer(@RequestBody CustomerRequestModel model) {
        var result = customerService.addCustomer(model);
        return ResponseEntity.created(linkTo(methodOn(CustomerController.class)
                .getCustomer(String.valueOf(result.getCustomerId()))).toUri()).build();
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerResponseModel> getCustomer(@PathVariable String customerId) {
        return customerService.getCustomer(customerId)
                .map(customerAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable String customerId, @RequestBody CustomerRequestModel model) {
        var result = customerService.updateCustomer(customerId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{customerId}/details")
    public ResponseEntity<CustomerDetailResponseModel> getCustomerDetails(@PathVariable String customerId) {
        return customerService.getCustomerDetails(customerId)
                .map(customerDetailsAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{customerId}/payments")
    public ResponseEntity<CollectionModel<PaymentResponseModel>> getCustomerPaymentList(@PathVariable String customerId) {
        return ResponseEntity.ok(paymentAssembler.toCollectionModel(
                customerService.getCustomerPaymentList(customerId)));
    }

    // @GetMapping(path = "/{customerId}/payments")
    // public ResponseEntity<CollectionModel<PaymentResponseModel>> getCustomerPayments(
    //         @PathVariable String customerId,
    //         @RequestParam(required = false) String start_date,
    //         @RequestParam(required = false) String end_date) {
    //     return ResponseEntity.ok(paymentAssembler.toCollectionModel(
    //             customerService.getCustomerPayments(customerId, start_date, end_date)));
    // }

    @GetMapping(path = "/{customerId}/rentals")
    public ResponseEntity<CollectionModel<RentalResponseModel>> getCustomerRentalList(@PathVariable String customerId) {
        return ResponseEntity.ok(rentalAssembler.toCollectionModel(
                customerService.getCustomerRentalList(customerId)));
    }

    // @GetMapping(path = "/{customerId}/rentals")
    // public ResponseEntity<CollectionModel<RentalResponseModel>> getCustomerRentals(
    //         @PathVariable String customerId,
    //         @RequestParam(required = false) String status,
    //         @RequestParam(required = false) String start_date,
    //         @RequestParam(required = false) String end_date) {
    //     return ResponseEntity.ok(rentalAssembler.toCollectionModel(
    //             customerService.getCustomerRentals(customerId, status, start_date, end_date)));
    // }
}
