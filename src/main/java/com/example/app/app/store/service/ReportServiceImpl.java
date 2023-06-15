package com.example.app.app.store.service;

import com.example.app.app.catalog.domain.dto.CategorySalesModel;
import com.example.app.app.payment.repository.PaymentRepository;
import com.example.app.app.store.domain.dto.StoreSalesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategorySalesModel> reportSalesByCategory() {
        return paymentRepository.calculateSalesByCategory();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreSalesModel> reportSalesByStore() {
        return paymentRepository.calculateSalesByStore();
    }
}
