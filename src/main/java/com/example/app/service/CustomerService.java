package com.example.app.service;

import com.example.app.model.internal.core.CustomerModel;
import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.internal.core.RentalModel;
import com.example.app.model.internal.extra.CustomerDetailModel;
import com.example.app.model.request.CustomerRequestModel;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerModel> getCustomers();

    Optional<CustomerModel> getCustomer(String customerId);

    List<CustomerDetailModel> getCustomersDetail();

    Optional<CustomerDetailModel> getCustomerDetail(String customerId);

    List<PaymentModel> getCustomerPayments(String customerId);

    List<PaymentModel> getCustomerPayments(String customerId, String startDate, String endDate);

    List<RentalModel> getCustomerRentals(String customerId);

    List<RentalModel> getCustomerRentals(String customerId, String status, String startDate, String endDate);

    CustomerModel addCustomer(CustomerRequestModel model);

    CustomerModel updateCustomer(String customerId, CustomerRequestModel model);

    void deleteCustomer(String customerId);
}
