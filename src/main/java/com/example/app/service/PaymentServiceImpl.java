package com.example.app.service;

import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.internal.CategorySalesModel;
import com.example.app.model.internal.StoreSalesModel;
import com.example.app.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;

    @Override
    public List<PaymentEntity> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<PaymentEntity> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<PaymentEntity> getAllPaymentsDetail() {
        return paymentRepository.findAllPaymentsDetail();
    }

    @Override
    public Optional<PaymentEntity> getPaymentDetailById(Integer id) {
        return paymentRepository.findPaymentDetailById(id);
    }

    @Override
    public PaymentEntity addPayment(PaymentEntity entity) {
        return paymentRepository.save(entity);
    }

    @Override
    public void updatePayment(PaymentEntity entity) {
        paymentRepository.save(entity);
    }

    @Override
    public void removePaymentById(Integer id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public List<CategorySalesModel> getSalesByCategory() {
        return paymentRepository.calculateSalesByCategory();
    }

    @Override
    public List<StoreSalesModel> getSalesByStore() {
        return paymentRepository.calculateSalesByStore();
    }
}
