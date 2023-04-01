package com.example.app.service;

import com.example.app.model.internal.core.CustomerModel;
import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.internal.core.RentalModel;
import com.example.app.model.internal.extra.CustomerDetailsModel;
import com.example.app.model.request.CustomerRequestModel;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerModel> getCustomerList();

    Optional<CustomerModel> getCustomer(String customerId);

    List<CustomerDetailsModel> getCustomerDetailsList();

    Optional<CustomerDetailsModel> getCustomerDetails(String customerId);

    List<PaymentModel> getCustomerPaymentList(String customerId);

    List<PaymentModel> getCustomerPaymentList(String customerId, String startDate, String endDate);

    List<RentalModel> getCustomerRentalList(String customerId);

    List<RentalModel> getCustomerRentalList(String customerId, String status, String startDate, String endDate);

    CustomerModel addCustomer(CustomerRequestModel model);

    CustomerModel updateCustomer(String customerId, CustomerRequestModel model);

    void deleteCustomer(String customerId);
}
