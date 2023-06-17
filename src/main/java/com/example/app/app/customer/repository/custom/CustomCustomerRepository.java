package com.example.app.app.customer.repository.custom;

import com.example.app.app.customer.domain.dto.CustomerDetailsDto;
import com.example.app.app.payment.domain.dto.PaymentDto;
import com.example.app.app.rental.domain.dto.RentalDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomCustomerRepository {
    List<CustomerDetailsDto.CustomerDetails> findAllCustomerDetailsList();

    Optional<CustomerDetailsDto.CustomerDetails> findCustomerDetailsById(Integer customerId);

    List<PaymentDto.Payment> findAllCustomerPaymentListById(Integer customerId);

    List<PaymentDto.Payment> findAllCustomerPaymentListByIdWithFilter(Integer customerId, LocalDate startDate, LocalDate endDate);

    List<RentalDto.Rental> findAllCustomerRentalListById(Integer customerId);

    List<RentalDto.Rental> findAllCustomerRentalListByIdWithFilter(Integer customerId, String status, LocalDate startDate, LocalDate endDate);
}
