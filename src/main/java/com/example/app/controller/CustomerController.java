package com.example.app.controller;

import com.example.app.hateoas.assembler.CustomerDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.CustomerRepresentationModelAssembler;
import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.request.CustomerRequestModel;
import com.example.app.model.response.CustomerDetailResponseModel;
import com.example.app.model.response.CustomerResponseModel;
import com.example.app.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepresentationModelAssembler customerAssembler;
    private final CustomerDetailRepresentationModelAssembler customerDetailAssembler;

    @GetMapping(path = "/customers")
    public ResponseEntity<CollectionModel<CustomerResponseModel>> getAllCustomers() {
        return ResponseEntity.ok(customerAssembler.toCollectionModel(customerService.getAllCustomers()));
    }

    @PostMapping(path = "/customers")
    public ResponseEntity<Void> addCustomer(@RequestBody CustomerRequestModel model) {
        var entity = new CustomerEntity();
        BeanUtils.copyProperties(model, entity);
        var result = customerService.addCustomer(entity);
        return ResponseEntity.created(linkTo(methodOn(CustomerController.class)
                .getCustomer(String.valueOf(result.getCustomerId()))).toUri()).build();
    }

    @GetMapping(path = "/customers/{id}")
    public ResponseEntity<CustomerResponseModel> getCustomer(@PathVariable String id) {
        return customerService.getCustomerById(Integer.valueOf(id))
                .map(customerAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/customers/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable String id, @RequestBody CustomerRequestModel model) {
        var entity = new CustomerEntity();
        BeanUtils.copyProperties(model, entity);
        entity.setCustomerId(Integer.valueOf(id));
        var result = customerService.updateCustomer(entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomerById(Integer.valueOf(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/customers/{id}/details")
    public ResponseEntity<CustomerDetailResponseModel> getCustomerDetail(@PathVariable String id) {
        return customerService.getCustomerDetailById(Integer.valueOf(id))
                .map(customerDetailAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
