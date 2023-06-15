package com.example.app.app.payment.service;

import com.example.app.app.payment.domain.dto.PaymentModel;
import com.example.app.app.payment.domain.dto.PaymentRequestModel;

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
