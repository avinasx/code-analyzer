package com.example.app.app.customer.repository.custom;

import com.example.app.app.customer.domain.dto.CustomerDetailsDto;
import com.example.app.app.payment.domain.dto.PaymentDto;
import com.example.app.app.rental.domain.dto.RentalDto;
import com.example.app.app.rental.domain.vo.RentalStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomCustomerRepository {
    List<CustomerDetailsDto.CustomerDetails> findAllCustomerDetailsList();

    Optional<CustomerDetailsDto.CustomerDetails> findCustomerDetailsById(Integer customerId);

    List<PaymentDto.Payment> findAllCustomerPaymentListByIdWithCondition(Integer customerId, LocalDate startDate, LocalDate endDate);

    List<RentalDto.Rental> findAllCustomerRentalListByIdWithCondition(Integer customerId, RentalStatus status, LocalDate startDate, LocalDate endDate);
}
