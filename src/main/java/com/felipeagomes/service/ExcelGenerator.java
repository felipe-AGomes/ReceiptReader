package com.felipeagomes.service;

import com.felipeagomes.products.Product;
import com.felipeagomes.receipt.ReceiptReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelGenerator {
    private final ReceiptReader receiptReader;

    public ExcelGenerator(ReceiptReader receiptReader) {
        this.receiptReader = receiptReader;
    }

    public void createReceiptExcelInto(Path resultPath) {
        try (HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(resultPath.toFile())) {

            HSSFSheet sheet = hssfWorkbook.createSheet("produtos");

            setHeader(sheet);
            setContent(sheet);

            hssfWorkbook.write(fileOut);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setContent(HSSFSheet sheet) {
        List<Product> products = receiptReader.getProducts();
        for (int i = 0; i < products.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            Product product = products.get(i);
            setProductRow(row, product);
        }
    }

    private void setHeader(HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        Product product = receiptReader.getProducts().getFirst();
        List<String> columns = product.getColumnNames();

        for (int i = 0; i < columns.size(); i++) {
            row.createCell(i).setCellValue(columns.get(i));
        }
    }

    private void setProductRow(HSSFRow row, Product product) {
        List<String> columns = product.getColumnNames();
        for (int i = 0; i < columns.size(); i++) {
            row.createCell(i).setCellValue(product.getPosition(i));
        }
    }
}
