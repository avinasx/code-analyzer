package com.example.app.service;

import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.internal.CustomerDetailModel;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerEntity> getAllCustomers();

    Optional<CustomerEntity> getCustomerById(Integer id);

    List<CustomerDetailModel> getAllCustomersDetail();

    Optional<CustomerDetailModel> getCustomerDetailById(Integer id);

    CustomerEntity addCustomer(CustomerEntity entity);

    void updateCustomer(CustomerEntity entity);

    void deleteCustomerById(Integer id);
}
