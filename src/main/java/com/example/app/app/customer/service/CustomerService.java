package com.example.app.app.customer.service;

import com.example.app.app.customer.domain.dto.CustomerDetailsDto;
import com.example.app.app.customer.domain.dto.CustomerDto;
import com.example.app.app.payment.domain.dto.PaymentDto;
import com.example.app.app.rental.domain.dto.RentalDto;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDto.Customer> getCustomerList();

    Optional<CustomerDto.Customer> getCustomer(String customerId);

    List<CustomerDetailsDto.CustomerDetails> getCustomerDetailsList();

    Optional<CustomerDetailsDto.CustomerDetails> getCustomerDetails(String customerId);

    List<PaymentDto.Payment> getCustomerPaymentList(String customerId);

    List<PaymentDto.Payment> getCustomerPaymentList(String customerId, String startDate, String endDate);

    List<RentalDto.Rental> getCustomerRentalList(String customerId);

    List<RentalDto.Rental> getCustomerRentalList(String customerId, String status, String startDate, String endDate);

    CustomerDto.Customer addCustomer(CustomerDto.CustomerRequest model);

    CustomerDto.Customer updateCustomer(String customerId, CustomerDto.CustomerRequest model);

    void deleteCustomer(String customerId);
}
