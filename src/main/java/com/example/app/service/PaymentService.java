package com.example.app.service;

import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.request.PaymentRequestModel;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<PaymentModel> getPayments();

    Optional<PaymentModel> getPayment(String paymentId);

    List<PaymentModel> getPaymentsDetail();

    Optional<PaymentModel> getPaymentDetail(String paymentId);

    PaymentModel addPayment(PaymentRequestModel model);

    PaymentModel updatePayment(String paymentId, PaymentRequestModel model);

    void deletePayment(String paymentId);
}
