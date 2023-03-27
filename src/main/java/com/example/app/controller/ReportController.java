package com.example.app.controller;

import com.example.app.hateoas.assembler.CategorySalesRepresentationModelAssembler;
import com.example.app.hateoas.assembler.StoreSalesRepresentationModelAssembler;
import com.example.app.model.response.CategorySalesResponseModel;
import com.example.app.model.response.StoreSalesResponseModel;
import com.example.app.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reports")
@AllArgsConstructor
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
