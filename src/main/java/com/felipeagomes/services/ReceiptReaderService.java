package com.felipeagomes.services;

import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.mappers.ProductsReceiptsMapper;
import com.felipeagomes.receipts.PDFReceiptReader;
import com.felipeagomes.receipts.interfaces.ReceiptReader;
import com.felipeagomes.repositories.ProductsReceiptsRepository;
import com.felipeagomes.utils.ProductsReceiptsUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.File;
import java.nio.file.Path;

public class ReceiptReaderService {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReceipReader");
    ProductsReceiptsRepository productsReceiptsRepository = new ProductsReceiptsRepository(emf);
    ProductsReceiptsMapper productsReceiptsMapper = new ProductsReceiptsMapper();

    public void saveReceiptAsPDF(File file) {
        ReceiptReader receiptReader = new PDFReceiptReader().readReceipt(file);
        receiptReader.setProducts(ProductsReceiptsUtil.aggregateProducts(receiptReader.getProducts()));

        productsReceiptsRepository.saveAll(productsReceiptsMapper.receiptReaderToProductsReceipts(receiptReader));
    }

    public void saveReceiptAsImage(File file) {
    }
}
