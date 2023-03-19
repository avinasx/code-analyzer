package com.example.app.service;

import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.internal.CustomerDetailModel;
import com.example.app.model.request.CustomerRequestModel;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerEntity> getAllCustomers();

    Optional<CustomerEntity> getCustomerById(String id);

    List<CustomerDetailModel> getAllCustomersDetail();

    Optional<CustomerDetailModel> getCustomerDetailById(String id);

    CustomerEntity addCustomer(CustomerRequestModel model);

    CustomerEntity updateCustomer(String id, CustomerRequestModel model);

    void deleteCustomerById(String id);
}
