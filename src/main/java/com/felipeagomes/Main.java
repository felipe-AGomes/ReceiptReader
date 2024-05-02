package com.felipeagomes;

import com.felipeagomes.receipt.ReceiptReader;
import com.felipeagomes.service.ExcelGenerator;

import java.io.File;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        ReceiptReader receiptReader = new ReceiptReader(new File("C:\\Users\\falme\\Downloads\\receipt.pdf"));

        System.out.println(receiptReader.getProducts());

        Path resultPath = Path.of("C:\\Users\\falme\\Downloads\\workshet.xls");
        ExcelGenerator excelGenerator = new ExcelGenerator(receiptReader);
        excelGenerator.createReceiptExcelInto(resultPath);
    }
}