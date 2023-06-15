package com.example.app.app.store.controller;

import com.example.app.app.store.assembler.CategorySalesRepresentationModelAssembler;
import com.example.app.app.store.assembler.StoreSalesRepresentationModelAssembler;
import com.example.app.app.store.domain.dto.CategorySalesResponseModel;
import com.example.app.app.store.domain.dto.StoreSalesResponseModel;
import com.example.app.app.store.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final CategorySalesRepresentationModelAssembler categorySalesAssembler;
    private final StoreSalesRepresentationModelAssembler storeSalesAssembler;

    @GetMapping(path = "/sales/categories")
    public ResponseEntity<CollectionModel<CategorySalesResponseModel>> reportSalesByCategory() {
        return ResponseEntity.ok(categorySalesAssembler.toCollectionModel(
                reportService.reportSalesByCategory()));
    }

    @GetMapping(path = "/sales/stores")
    public ResponseEntity<CollectionModel<StoreSalesResponseModel>> reportSalesByStore() {
        return ResponseEntity.ok(storeSalesAssembler.toCollectionModel(
                reportService.reportSalesByStore()));
    }
}
