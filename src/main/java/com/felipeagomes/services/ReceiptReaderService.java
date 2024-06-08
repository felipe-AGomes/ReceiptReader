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
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReceipReader");
    private final ProductsReceiptsRepository productsReceiptsRepository = new ProductsReceiptsRepository(emf);
    private final ProductsReceiptsMapper productsReceiptsMapper = new ProductsReceiptsMapper();
    private final ProductsReceiptsService productsReceiptsService = new ProductsReceiptsService(productsReceiptsRepository, productsReceiptsMapper);

    public void saveReceiptAsPDF(File file) {
        ReceiptReader receiptReader = new PDFReceiptReader().readReceipt(file);
        receiptReader.setProducts(ProductsReceiptsUtil.aggregateProducts(receiptReader.getProducts()));

        productsReceiptsService.saveAllProductsReceipts(productsReceiptsMapper.receiptReaderToProductsReceipts(receiptReader));
    }

    public void saveReceiptAsImage(File file) {
    }
}
