package com.example.app.repository.custom;

import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.internal.extra.CategorySalesModel;
import com.example.app.model.internal.extra.StoreSalesModel;

import java.util.List;
import java.util.Optional;

public interface CustomPaymentRepository {
    List<PaymentModel> findAllPaymentDetailsList();

    Optional<PaymentModel> findPaymentDetailsById(Integer paymentId);

    List<CategorySalesModel> calculateSalesByCategory();

    List<StoreSalesModel> calculateSalesByStore();
}
