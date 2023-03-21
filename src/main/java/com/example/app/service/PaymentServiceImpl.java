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
        var result = paymentRepository.findAll();
        return paymentMapper.mapToDtoList(result);
    }

    @Override
    public Optional<PaymentModel> getPaymentById(String id) {
        var result = paymentRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Payment not found with id '" + id + "'"));
        return Optional.of(paymentMapper.mapToDto(entity));
    }

    @Override
    public List<PaymentModel> getAllPaymentsDetail() {
        return paymentRepository.findAllPaymentsDetail();
    }

    @Override
    public Optional<PaymentModel> getPaymentDetailById(String id) {
        var result = paymentRepository.findPaymentDetailById(Integer.valueOf(id));
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Payment not found with id '" + id + "'");
        }
        return result;
    }

    @Override
    public PaymentModel addPayment(PaymentRequestModel model) {
        var result = paymentMapper.mapToEntity(model);
        var entity = paymentRepository.save(result);
        return paymentMapper.mapToDto(entity);
    }

    @Override
    public PaymentModel updatePayment(String id, PaymentRequestModel model) {
        var result = paymentRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Payment not found with id '" + id + "'"));
        paymentMapper.updateEntity(model, entity);
        var updated = paymentRepository.save(entity);
        return paymentMapper.mapToDto(updated);
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
