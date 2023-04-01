package com.example.app.service;

import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.request.PaymentRequestModel;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<PaymentModel> getPaymentList();

    Optional<PaymentModel> getPayment(String paymentId);

    List<PaymentModel> getPaymentDetailsList();

    Optional<PaymentModel> getPaymentDetails(String paymentId);

    PaymentModel addPayment(PaymentRequestModel model);

    PaymentModel updatePayment(String paymentId, PaymentRequestModel model);

    void deletePayment(String paymentId);
}
