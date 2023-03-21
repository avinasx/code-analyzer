package com.example.app.service;

import com.example.app.model.internal.CategorySalesModel;
import com.example.app.model.internal.PaymentModel;
import com.example.app.model.internal.StoreSalesModel;
import com.example.app.model.request.PaymentRequestModel;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<PaymentModel> getAllPayments();

    Optional<PaymentModel> getPaymentById(String id);

    List<PaymentModel> getAllPaymentsDetail();

    Optional<PaymentModel> getPaymentDetailById(String id);

    PaymentModel addPayment(PaymentRequestModel model);

    PaymentModel updatePayment(String id, PaymentRequestModel model);

    void removePaymentById(String id);

    List<CategorySalesModel> getSalesByCategory();

    List<StoreSalesModel> getSalesByStore();
}
