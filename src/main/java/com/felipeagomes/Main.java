package com.felipeagomes;

import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.receipts.ReceiptReader;
import com.felipeagomes.repositories.ProductsReceiptsRepository;
import com.felipeagomes.services.ProductsReceiptsService;
import com.felipeagomes.utils.ProductsReceiptsUtil;

import java.io.File;
import java.util.List;

public class Main {
    private static final ProductsReceiptsService productsReceiptsService = new ProductsReceiptsService(new ProductsReceiptsRepository());

    public static void main(String[] args) {
        saveProductsReceipts("C:\\Users\\falme\\Downloads\\Consulta Pública de NFCe (2).pdf");
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

//    private static void createExcelReceipt() {
//        final String XLSX_EXTENSION = ".xls";
//
//        ReceiptReader receiptReader = getReceiptReader();
//
//        Path resultPath = Path.of("C:\\Users\\falme\\Downloads\\" + receiptReader.getTitle() + XLSX_EXTENSION);
//
//        ExcelReceiptBuilder excelBuilder = new ExcelReceiptBuilder();
//
//        excelBuilder
//                .setResultPath(resultPath)
//                .setReceipt(receiptReader.getReceipt())
//                .setTitle(receiptReader.getTitle());
//
//        excelBuilder.create();
//    }
}