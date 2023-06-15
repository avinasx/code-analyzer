package com.example.app.app.store.service;

import com.example.app.app.catalog.domain.dto.CategorySalesModel;
import com.example.app.app.store.domain.dto.StoreSalesModel;

import java.util.List;

public interface ReportService {

    List<CategorySalesModel> reportSalesByCategory();

    List<StoreSalesModel> reportSalesByStore();
}
