package com.example.app.app.payment.service;

import com.example.app.app.payment.domain.dto.PaymentModel;
import com.example.app.app.payment.domain.dto.PaymentRequestModel;
import com.example.app.app.payment.domain.mapper.PaymentMapper;
import com.example.app.app.payment.repository.PaymentRepository;
import com.example.app.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentModel> getPaymentList() {
        var list = paymentRepository.findAll();
        return paymentMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentModel> getPayment(String paymentId) {
        var entity = paymentRepository.findById(Integer.valueOf(paymentId)).orElseThrow(() ->
                new ResourceNotFoundException("Payment not found with id '" + paymentId + "'"));
        return Optional.of(paymentMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentModel> getPaymentDetailsList() {
        return paymentRepository.findAllPaymentDetailsList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentModel> getPaymentDetails(String paymentId) {
        var model = paymentRepository.findPaymentDetailsById(Integer.valueOf(paymentId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Payment not found with id '" + paymentId + "'");
        }
        return model;
    }

    @Override
    @Transactional
    public PaymentModel addPayment(PaymentRequestModel model) {
        var entity = paymentMapper.mapToEntity(model);
        var savedEntity = paymentRepository.save(entity);
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
