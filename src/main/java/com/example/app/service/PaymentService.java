package com.example.app.service;

import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.internal.CategorySalesModel;
import com.example.app.model.internal.StoreSalesModel;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<PaymentEntity> getAllPayments();

    Optional<PaymentEntity> getPaymentById(Integer id);

    List<PaymentEntity> getAllPaymentsDetail();

    Optional<PaymentEntity> getPaymentDetailById(Integer id);

    void addPayment(PaymentEntity entity);

    void updatePayment(PaymentEntity entity);

    void removePaymentById(Integer id);

    List<CategorySalesModel> getSalesByCategory();

    List<StoreSalesModel> getSalesByStore();
}
