package com.felipeagomes;

import com.felipeagomes.receipt.ReceiptReader;
import com.felipeagomes.service.ExcelReceiptBuilder;

import java.io.File;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        final String XLSX_EXTENSION = ".xls";

        ReceiptReader receiptReader = new ReceiptReader(new File("C:\\Users\\falme\\Downloads\\receipt.pdf"));

        /*System.out.println(receiptReader.getProducts());
        System.out.println(receiptReader.getCompanyName());
        System.out.println(receiptReader.getDate());
        System.out.println(receiptReader.getTitle());*/

        Path resultPath = Path.of("C:\\Users\\falme\\Downloads\\" + receiptReader.getTitle() + XLSX_EXTENSION);

        ExcelReceiptBuilder excelBuilder = new ExcelReceiptBuilder();

        excelBuilder
                .setResultPath(resultPath)
                .setReceipt(receiptReader.getReceipt())
                .setTitle(receiptReader.getTitle());

        excelBuilder.create();

    }
}