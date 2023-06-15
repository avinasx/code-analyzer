package com.example.app.app.customer.service;

import com.example.app.app.customer.domain.dto.CustomerDetailsModel;
import com.example.app.app.customer.domain.dto.CustomerModel;
import com.example.app.app.customer.domain.dto.CustomerRequestModel;
import com.example.app.app.payment.domain.dto.PaymentModel;
import com.example.app.app.rental.domain.dto.RentalModel;

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
