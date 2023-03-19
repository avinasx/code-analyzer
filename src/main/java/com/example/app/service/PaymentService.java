package com.example.app.service;

import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.internal.CategorySalesModel;
import com.example.app.model.internal.StoreSalesModel;
import com.example.app.model.request.PaymentRequestModel;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<PaymentEntity> getAllPayments();

    Optional<PaymentEntity> getPaymentById(String id);

    List<PaymentEntity> getAllPaymentsDetail();

    Optional<PaymentEntity> getPaymentDetailById(String id);

    PaymentEntity addPayment(PaymentRequestModel model);

    PaymentEntity updatePayment(String id, PaymentRequestModel model);

    void removePaymentById(String id);

    List<CategorySalesModel> getSalesByCategory();

    List<StoreSalesModel> getSalesByStore();
}
