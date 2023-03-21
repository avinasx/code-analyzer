package com.example.app.repository.custom;

import com.example.app.model.internal.CategorySalesModel;
import com.example.app.model.internal.PaymentModel;
import com.example.app.model.internal.StoreSalesModel;

import java.util.List;
import java.util.Optional;

public interface CustomPaymentRepository {
    List<PaymentModel> findAllPaymentsDetail();

    Optional<PaymentModel> findPaymentDetailById(Integer id);

    List<CategorySalesModel> calculateSalesByCategory();

    List<StoreSalesModel> calculateSalesByStore();
}
