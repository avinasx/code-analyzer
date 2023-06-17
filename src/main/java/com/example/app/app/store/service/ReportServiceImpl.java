package com.example.app.app.store.service;

import com.example.app.app.payment.repository.PaymentRepository;
import com.example.app.app.store.domain.dto.CategorySalesDto;
import com.example.app.app.store.domain.dto.StoreSalesDto;
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
    public List<CategorySalesDto.CategorySales> reportSalesByCategory() {
        return paymentRepository.calculateSalesByCategory();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreSalesDto.StoreSales> reportSalesByStore() {
        return paymentRepository.calculateSalesByStore();
    }
}
