package com.example.app.service;

import com.example.app.model.internal.CustomerDetailModel;
import com.example.app.model.internal.CustomerModel;
import com.example.app.model.request.CustomerRequestModel;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerModel> getAllCustomers();

    Optional<CustomerModel> getCustomerById(String id);

    List<CustomerDetailModel> getAllCustomersDetail();

    Optional<CustomerDetailModel> getCustomerDetailById(String id);

    CustomerModel addCustomer(CustomerRequestModel model);

    CustomerModel updateCustomer(String id, CustomerRequestModel model);

    void deleteCustomerById(String id);
}
