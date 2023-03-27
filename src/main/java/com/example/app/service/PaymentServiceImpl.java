package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.mapping.mapper.PaymentMapper;
import com.example.app.model.request.PaymentRequestModel;
import com.example.app.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentModel> getPayments() {
        var list = paymentRepository.findAll();
        return paymentMapper.mapToDtoList(list);
    }

    @Override
    public Optional<PaymentModel> getPayment(String paymentId) {
        var entity = paymentRepository.findById(Integer.valueOf(paymentId)).orElseThrow(() ->
                new ResourceNotFoundException("Payment not found with id '" + paymentId + "'"));
        return Optional.of(paymentMapper.mapToDto(entity));
    }

    @Override
    public List<PaymentModel> getPaymentsDetail() {
        return paymentRepository.findAllPaymentsDetail();
    }

    @Override
    public Optional<PaymentModel> getPaymentDetail(String paymentId) {
        var model = paymentRepository.findPaymentDetailById(Integer.valueOf(paymentId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Payment not found with id '" + paymentId + "'");
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
    @Transactional
    public PaymentModel updatePayment(String paymentId, PaymentRequestModel model) {
        var entity = paymentRepository.findById(Integer.valueOf(paymentId)).orElseThrow(() ->
                new ResourceNotFoundException("Payment not found with id '" + paymentId + "'"));
        entity.update(paymentMapper.mapToEntity(model));
        return paymentMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void deletePayment(String paymentId) {
        paymentRepository.deleteById(Integer.valueOf(paymentId));
    }
}
