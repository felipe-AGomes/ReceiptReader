package com.felipeagomes.service;

import com.felipeagomes.products.Product;
import com.felipeagomes.products.ProductColumn;
import com.felipeagomes.receipt.Receipt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelReceiptBuilder {
    private Receipt receipt;
    private String title;
    private Path resultPath;

    public void create() {
        try (HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(getResultPath())) {
            HSSFSheet sheet = hssfWorkbook.createSheet(getTitle());

            setHeader(sheet);
            setContent(sheet);

            hssfWorkbook.write(fileOut);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private File getResultPath() {
        if (this.resultPath == null) {
            throw new RuntimeException("Nenhum caminho para salvar o excel definido");
        }
        return resultPath.toFile();
    }

    private void setContent(HSSFSheet sheet) {
        List<Product> products = getReceipt().products();
        for (int i = 0; i < products.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            Product product = products.get(i);
            setProductRow(row, product);
        }
    }

    private void setHeader(HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < ProductColumn.values().length; i++) {
            ProductColumn column = ProductColumn.values()[i];
            row.createCell(i).setCellValue(column.toString());
        }
    }

    private void setProductRow(HSSFRow row, Product product) {
        for (ProductColumn column : ProductColumn.values()) {
            int index = column.ordinal();
            Object value = product.getPosition(column);
            createCell(row, index, value);
        }
    }

    private void createCell(HSSFRow row, int index, Object value) {
        HSSFCell cell = row.createCell(index);
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + value.getClass().getName());
        }
    }

    public Receipt getReceipt() {
        if (this.receipt == null) {
            throw new RuntimeException("Nenhum recibo definido");
        }

        return this.receipt;
    }

    public ExcelReceiptBuilder setReceipt(Receipt receipt) {
        this.receipt = receipt;

        return this;
    }

    public String getTitle() {
        if (this.title == null) {
            return "Sem Título";
        }

        return this.title;
    }

    public ExcelReceiptBuilder setTitle(String title) {
        this.title = title;

        return this;
    }

    public ExcelReceiptBuilder setResultPath(Path resultPath) {
        this.resultPath = resultPath;

        return this;
    }
}
