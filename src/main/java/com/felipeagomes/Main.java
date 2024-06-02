package com.felipeagomes;

import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.receipts.ReceiptReader;
import com.felipeagomes.reports.ExcelReportBuilder;
import com.felipeagomes.repositories.ProductsReceiptsRepository;
import com.felipeagomes.services.ProductsReceiptsService;
import com.felipeagomes.utils.ProductsReceiptsUtil;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class Main {
    private static final ProductsReceiptsService productsReceiptsService = new ProductsReceiptsService(new ProductsReceiptsRepository());

    public static void main(String[] args) {
        //saveProductsReceipts("C:\\Users\\falme\\Downloads\\Consulta Pública de NFCe (2).pdf");

        createExcelReportForQuery("SELECT e FROM ProductsReceipts e");
    }

    private static void createExcelReportForQuery(String query) {
        final String TITLE_REPORT = "RELATÓRIO_RECIBOS";
        final Path RESULT_PATH = Path.of("C:\\Users\\falme\\Downloads\\result_path");

        ExcelReportBuilder excelReportBuilder = new ExcelReportBuilder();

        excelReportBuilder
                .query(query)
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