package com.felipeagomes;

import com.felipeagomes.products.Product;
import com.felipeagomes.products.ProductAggregator;
import com.felipeagomes.receipt.ReceiptReader;
import com.felipeagomes.service.ExcelReceiptBuilder;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final String XLSX_EXTENSION = ".xls";

        ReceiptReader receiptReader = new ReceiptReader(new File("C:\\Users\\falme\\Downloads\\receipt.pdf"));

        receiptReader.setProducts(aggregateProducts(receiptReader));

        Path resultPath = Path.of("C:\\Users\\falme\\Downloads\\" + receiptReader.getTitle() + XLSX_EXTENSION);

        ExcelReceiptBuilder excelBuilder = new ExcelReceiptBuilder();

        excelBuilder
                .setResultPath(resultPath)
                .setReceipt(receiptReader.getReceipt())
                .setTitle(receiptReader.getTitle());

        excelBuilder.create();

    }

    private static List<Product> aggregateProducts(ReceiptReader receiptReader) {
        ProductAggregator aggregator = new ProductAggregator();
        List<Product> products = receiptReader.getProducts();
        return aggregator.aggregateByCodigo(products);
    }
}