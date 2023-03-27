package com.example.app.repository.custom;

import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.internal.core.RentalModel;
import com.example.app.model.internal.extra.CustomerDetailModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomCustomerRepository {
    List<CustomerDetailModel> findAllCustomersDetail();

    Optional<CustomerDetailModel> findCustomerDetailById(Integer customerId);

    List<PaymentModel> findAllCustomerPaymentsById(Integer customerId);

    List<PaymentModel> findAllCustomerPaymentsByIdWithFilter(Integer customerId, LocalDate startDate, LocalDate endDate);

    List<RentalModel> findAllCustomerRentalsById(Integer customerId);

    List<RentalModel> findAllCustomerRentalsByIdWithFilter(Integer customerId, String status, LocalDate startDate, LocalDate endDate);
}
