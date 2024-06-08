package com.felipeagomes;

import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.mappers.ProductsReceiptsMapper;
import com.felipeagomes.receipts.PDFReceiptReader;
import com.felipeagomes.receipts.interfaces.ReceiptReader;
import com.felipeagomes.reports.excel.ExcelReportBuilder;
import com.felipeagomes.repositories.ProductsReceiptsRepository;
import com.felipeagomes.services.ProductsReceiptsService;
import com.felipeagomes.services.ReceiptReaderService;
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
    final private static ProductsReceiptsService productsReceiptsService = new ProductsReceiptsService(productsReceiptsRepository, productsReceiptsMapper);

    public static void main(String[] args) {
        ReceiptReaderService receiptReaderService = new ReceiptReaderService();

        receiptReaderService.saveReceiptAsPDF(new File("C:\\\\Users\\\\falme\\\\Downloads\\\\Consulta Pública de NFCe (2).pdf"));
        receiptReaderService.saveReceiptAsImage(new File("C:\\\\Users\\\\falme\\\\Downloads\\\\Consulta Pública de NFCe (2).pdf"));

        //createExcelReportForQuery("ProductsReceipts.findAll", ProductsReceiptsWithTotValueDto.class);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T> void createExcelReportForQuery(String namedQuery,
                                                      Class<T> structure) {
        final String TITLE_REPORT = "RELATÓRIO_RECIBOS";
        final Path RESULT_PATH = Path.of("C:\\Users\\falme\\Downloads\\result_path");

        ExcelReportBuilder<T> excelReportBuilder = new ExcelReportBuilder<>(productsReceiptsService);

        excelReportBuilder
                .query(namedQuery)
                .structure(structure)
                .title(TITLE_REPORT)
                .resultPath(RESULT_PATH)
                .build();

    }
}