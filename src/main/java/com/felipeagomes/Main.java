package com.felipeagomes;

import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.products.Product;
import com.felipeagomes.products.ProductAggregator;
import com.felipeagomes.receipt.ReceiptReader;
import com.felipeagomes.service.ExcelReceiptBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.apache.poi.hpsf.HPSFRuntimeException;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ReceiptReader receiptReader = getReceiptReader();
        List<ProductsReceipts> productsReceipts = receiptReaderToProductsReceipts(receiptReader);

        EntityTransaction tr = null;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReceipReader");
             EntityManager em = emf.createEntityManager()) {

            tr = em.getTransaction();
            tr.begin();

            for (ProductsReceipts productReceipt : productsReceipts) {
                em.persist(productReceipt);
            }

            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
        }

    }

    private static List<ProductsReceipts> receiptReaderToProductsReceipts(ReceiptReader receiptReader) {
        List<ProductsReceipts> productsReceipts = new ArrayList<>();
        for (Product product : receiptReader.getProducts()) {
            BigDecimal valor = BigDecimal.valueOf(product.getValor());
            ProductsReceipts productReceipt = new ProductsReceipts(product.getCodigo(), product.getNome(), product.getQuantidade(), product.getUnidade(), valor);
            productReceipt.setSupermarket(receiptReader.getCompanyName());
            productReceipt.setPurchaseDate(receiptReader.getDate());

            productsReceipts.add(productReceipt);
        }

        return productsReceipts;
    }

    private static void createExcelReceipt() {
        final String XLSX_EXTENSION = ".xls";

        ReceiptReader receiptReader = getReceiptReader();

        Path resultPath = Path.of("C:\\Users\\falme\\Downloads\\" + receiptReader.getTitle() + XLSX_EXTENSION);

        ExcelReceiptBuilder excelBuilder = new ExcelReceiptBuilder();

        excelBuilder
                .setResultPath(resultPath)
                .setReceipt(receiptReader.getReceipt())
                .setTitle(receiptReader.getTitle());

        excelBuilder.create();
    }

    private static ReceiptReader getReceiptReader() {
        ReceiptReader receiptReader = new ReceiptReader(new File("C:\\Users\\falme\\Downloads\\Consulta Pública de NFCe (2).pdf"));
        receiptReader.setProducts(aggregateProducts(receiptReader));

        return receiptReader;
    }

    private static List<Product> aggregateProducts(ReceiptReader receiptReader) {
        ProductAggregator aggregator = new ProductAggregator();
        List<Product> products = receiptReader.getProducts();
        return aggregator.aggregateByCodigo(products);
    }
}