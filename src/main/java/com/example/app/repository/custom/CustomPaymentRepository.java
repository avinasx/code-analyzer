package com.example.app.repository.custom;

import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.internal.CategorySalesModel;
import com.example.app.model.internal.StoreSalesModel;

import java.util.List;
import java.util.Optional;

public interface CustomPaymentRepository {
    List<PaymentEntity> findAllPaymentsDetail();

    Optional<PaymentEntity> findPaymentDetailById(Integer id);

    List<CategorySalesModel> calculateSalesByCategory();

    List<StoreSalesModel> calculateSalesByStore();
}
