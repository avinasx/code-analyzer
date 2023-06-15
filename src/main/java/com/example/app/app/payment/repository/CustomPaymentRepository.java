package com.example.app.app.payment.repository;

import com.example.app.app.catalog.domain.dto.CategorySalesModel;
import com.example.app.app.payment.domain.dto.PaymentModel;
import com.example.app.app.store.domain.dto.StoreSalesModel;

import java.util.List;
import java.util.Optional;

public interface CustomPaymentRepository {
    List<PaymentModel> findAllPaymentDetailsList();

    Optional<PaymentModel> findPaymentDetailsById(Integer paymentId);

    List<CategorySalesModel> calculateSalesByCategory();

    List<StoreSalesModel> calculateSalesByStore();
}
