package com.felipeagomes;

import com.felipeagomes.dtos.ProductsReceiptsWithTotValueDto;
import com.felipeagomes.services.ReceiptReaderService;
import com.felipeagomes.services.ReportService;

import java.nio.file.Path;

public class Main {
    final private static ReceiptReaderService receiptReaderService = new ReceiptReaderService();
    final private static ReportService reportService = new ReportService();

    public static void main(String[] args) {
        // receiptReaderService.saveReceiptAsPDF(new File("C:\\\\Users\\\\falme\\\\Downloads\\\\Consulta Pública de NFCe (2).pdf"));
        // receiptReaderService.saveReceiptAsImage(new File("C:\\\\Users\\\\falme\\\\Downloads\\\\Consulta Pública de NFCe (2).pdf"));

        reportService.generateExcelReportForQueryToPath("ProductsReceipts.findAll", ProductsReceiptsWithTotValueDto.class, Path.of("C:\\Users\\falme\\Downloads\\result_path"));
    }
}