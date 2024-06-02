package com.felipeagomes.services;

import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.repositories.ProductsReceiptsRepository;

import java.util.List;

public class ProductsReceiptsService {
    private final ProductsReceiptsRepository productsReceiptsRepository;

    public ProductsReceiptsService(ProductsReceiptsRepository productsReceiptsRepository) {
        this.productsReceiptsRepository = productsReceiptsRepository;
    }

    public void saveAllProductsReceipts(List<ProductsReceipts> productsReceipts) {
        productsReceiptsRepository.saveAll(productsReceipts);
    }
}
