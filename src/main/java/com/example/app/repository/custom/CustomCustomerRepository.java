package com.example.app.repository.custom;

import com.example.app.model.internal.CustomerDetailModel;

import java.util.List;
import java.util.Optional;

public interface CustomCustomerRepository {
    List<CustomerDetailModel> findAllCustomersDetail();

    Optional<CustomerDetailModel> findCustomerDetailById(Integer id);
}
