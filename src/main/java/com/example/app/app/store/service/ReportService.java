package com.example.app.app.store.service;

import com.example.app.app.store.domain.dto.CategorySalesDto;
import com.example.app.app.store.domain.dto.StoreSalesDto;

import java.util.List;

public interface ReportService {

    List<CategorySalesDto.CategorySales> reportSalesByCategory();

    List<StoreSalesDto.StoreSales> reportSalesByStore();
}
