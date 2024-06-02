package com.felipeagomes;

import com.felipeagomes.dtos.ProductsReceiptsDto;
import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.mappers.ProductsReceiptsMapper;
import com.felipeagomes.receipts.ReceiptReader;
import com.felipeagomes.reports.excel.ExcelReportBuilder;
import com.felipeagomes.repositories.ProductsReceiptsRepository;
import com.felipeagomes.services.ProductsReceiptsService;
import com.felipeagomes.utils.ProductsReceiptsUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class Main {
    final private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReceipReader");
    final private static ProductsReceiptsRepository productsReceiptsRepository = new ProductsReceiptsRepository(emf);
    final private static ProductsReceiptsMapper productsReceiptsMapper = new ProductsReceiptsMapper();
    final private static ProductsReceiptsService productsReceiptsService = new ProductsReceiptsService(productsReceiptsRepository);

    public static void main(String[] args) {
        //saveProductsReceipts("C:\\Users\\falme\\Downloads\\Consulta Pública de NFCe (2).pdf");

        createExcelReportForQuery("ProductsReceipts.findAll");
    }

    private static void createExcelReportForQuery(String namedQuery) {
        final String TITLE_REPORT = "RELATÓRIO_RECIBOS";
        final Path RESULT_PATH = Path.of("C:\\Users\\falme\\Downloads\\result_path");

        ExcelReportBuilder<ProductsReceiptsDto> excelReportBuilder = new ExcelReportBuilder<>(productsReceiptsService, productsReceiptsMapper);

        excelReportBuilder
                .query(namedQuery)
                .structure(ProductsReceiptsDto.class)
                .title(TITLE_REPORT)
                .resultPath(RESULT_PATH)
                .build();

    }

    private static void saveProductsReceipts(String path) {
        ReceiptReader receiptReader = getReceiptReader(path);
        List<ProductsReceipts> productsReceipts = ProductsReceiptsUtil.receiptReaderToProductsReceipts(receiptReader);

        productsReceiptsService.saveAllProductsReceipts(productsReceipts);
    }

    private static ReceiptReader getReceiptReader(String path) {
        ReceiptReader receiptReader = new ReceiptReader(new File(path));
        receiptReader.setProducts(ProductsReceiptsUtil.aggregateProducts(receiptReader));

        return receiptReader;
    }
}