package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.CategorySalesModel;
import com.example.app.model.internal.PaymentModel;
import com.example.app.model.internal.StoreSalesModel;
import com.example.app.model.mapping.mapper.PaymentMapper;
import com.example.app.model.request.PaymentRequestModel;
import com.example.app.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentModel> getAllPayments() {
        var list = paymentRepository.findAll();
        return paymentMapper.mapToDtoList(list);
    }

    @Override
    public Optional<PaymentModel> getPaymentById(String id) {
        var entity = paymentRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Payment not found with id '" + id + "'"));
        return Optional.of(paymentMapper.mapToDto(entity));
    }

    @Override
    public List<PaymentModel> getAllPaymentsDetail() {
        return paymentRepository.findAllPaymentsDetail();
    }

    @Override
    public Optional<PaymentModel> getPaymentDetailById(String id) {
        var model = paymentRepository.findPaymentDetailById(Integer.valueOf(id));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Payment not found with id '" + id + "'");
        }
        return model;
    }

    @Override
    public PaymentModel addPayment(PaymentRequestModel model) {
        var result = paymentMapper.mapToEntity(model);
        var savedEntity = paymentRepository.save(result);
        return paymentMapper.mapToDto(savedEntity);
    }

    @Override
    public PaymentModel updatePayment(String id, PaymentRequestModel model) {
        var entity = paymentRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Payment not found with id '" + id + "'"));
        entity.update(paymentMapper.mapToEntity(model));
        return paymentMapper.mapToDto(entity);
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
