package com.felipeagomes.services;

import com.felipeagomes.mappers.ProductsReceiptsMapper;
import com.felipeagomes.reports.excel.ExcelReportBuilder;
import com.felipeagomes.repositories.ProductsReceiptsRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.nio.file.Path;

public class ReportService {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReceipReader");
    private final ProductsReceiptsRepository productsReceiptsRepository = new ProductsReceiptsRepository(emf);
    private final ProductsReceiptsMapper productsReceiptsMapper = new ProductsReceiptsMapper();
    private final ProductsReceiptsService productsReceiptsService = new ProductsReceiptsService(productsReceiptsRepository, productsReceiptsMapper);

    public <T> void generateExcelReportForQueryToPath(String namedQuery, Class<T> structure, Path resultPath) {
        final String TITLE_REPORT = "RELATÓRIO_RECIBOS";

        ExcelReportBuilder<T> excelReportBuilder = new ExcelReportBuilder<>(productsReceiptsService);

        excelReportBuilder
                .query(namedQuery)
                .structure(structure)
                .title(TITLE_REPORT)
                .resultPath(resultPath)
                .build();

    }
}
