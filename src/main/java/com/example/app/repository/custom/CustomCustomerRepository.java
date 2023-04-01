package com.example.app.repository.custom;

import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.internal.core.RentalModel;
import com.example.app.model.internal.extra.CustomerDetailsModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomCustomerRepository {
    List<CustomerDetailsModel> findAllCustomerDetailsList();

    Optional<CustomerDetailsModel> findCustomerDetailsById(Integer customerId);

    List<PaymentModel> findAllCustomerPaymentListById(Integer customerId);

    List<PaymentModel> findAllCustomerPaymentListByIdWithFilter(Integer customerId, LocalDate startDate, LocalDate endDate);

    List<RentalModel> findAllCustomerRentalListById(Integer customerId);

    List<RentalModel> findAllCustomerRentalListByIdWithFilter(Integer customerId, String status, LocalDate startDate, LocalDate endDate);
}
