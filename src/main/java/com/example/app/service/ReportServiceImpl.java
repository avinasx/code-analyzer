package com.example.app.service;

import com.example.app.model.internal.extra.CategorySalesModel;
import com.example.app.model.internal.extra.StoreSalesModel;
import com.example.app.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PaymentRepository paymentRepository;

    @Override
    public List<CategorySalesModel> reportSalesByCategory() {
        return paymentRepository.calculateSalesByCategory();
    }

    @Override
    public List<StoreSalesModel> reportSalesByStore() {
        return paymentRepository.calculateSalesByStore();
    }
}
