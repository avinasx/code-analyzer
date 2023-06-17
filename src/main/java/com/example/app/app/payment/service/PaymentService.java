package com.example.app.app.payment.service;

import com.example.app.app.payment.domain.dto.PaymentDto;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<PaymentDto.Payment> getPaymentList();

    Optional<PaymentDto.Payment> getPayment(String paymentId);

    List<PaymentDto.Payment> getPaymentDetailsList();

    Optional<PaymentDto.Payment> getPaymentDetails(String paymentId);

    PaymentDto.Payment addPayment(PaymentDto.PaymentRequest model);

    PaymentDto.Payment updatePayment(String paymentId, PaymentDto.PaymentRequest model);

    void deletePayment(String paymentId);
}
