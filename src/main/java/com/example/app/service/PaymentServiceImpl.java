package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.internal.CategorySalesModel;
import com.example.app.model.internal.StoreSalesModel;
import com.example.app.model.mapping.CopyUtils;
import com.example.app.model.request.PaymentRequestModel;
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
    public Optional<PaymentEntity> getPaymentById(String id) {
        return paymentRepository.findById(Integer.valueOf(id));
    }

    @Override
    public List<PaymentEntity> getAllPaymentsDetail() {
        return paymentRepository.findAllPaymentsDetail();
    }

    @Override
    public Optional<PaymentEntity> getPaymentDetailById(String id) {
        return paymentRepository.findPaymentDetailById(Integer.valueOf(id));
    }

    @Override
    public PaymentEntity addPayment(PaymentRequestModel model) {
        var entity = new PaymentEntity();
        CopyUtils.copyNonNullProperties(model, entity);
        return paymentRepository.save(entity);
    }

    @Override
    public PaymentEntity updatePayment(String id, PaymentRequestModel model) {
        var resource = paymentRepository.findById(Integer.valueOf(id));
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException("Payment not found with id '" + id + "'");
        }
        var entity = resource.get();
        CopyUtils.copyNonNullProperties(model, entity);
        return paymentRepository.save(entity);
    }

    @Override
    public void removePaymentById(String id) {
        paymentRepository.deleteById(Integer.valueOf(id));
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
