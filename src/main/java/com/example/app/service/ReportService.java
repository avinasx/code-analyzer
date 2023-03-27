package com.example.app.service;

import com.example.app.model.internal.extra.CategorySalesModel;
import com.example.app.model.internal.extra.StoreSalesModel;

import java.util.List;

public interface ReportService {

    List<CategorySalesModel> reportSalesByCategory();

    List<StoreSalesModel> reportSalesByStore();
}
