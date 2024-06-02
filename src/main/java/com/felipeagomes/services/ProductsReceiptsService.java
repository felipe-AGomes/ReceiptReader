package com.felipeagomes.services;

import com.felipeagomes.dtos.ProductsReceiptsDto;
import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.mappers.ProductsReceiptsMapper;
import com.felipeagomes.repositories.ProductsReceiptsRepository;
import com.felipeagomes.repositories.interfaces.QueryService;

import java.util.List;

public class ProductsReceiptsService implements QueryService {
    private final ProductsReceiptsRepository productsReceiptsRepository;

    public ProductsReceiptsService(ProductsReceiptsRepository productsReceiptsRepository) {
        this.productsReceiptsRepository = productsReceiptsRepository;
    }

    public void saveAllProductsReceipts(List<ProductsReceipts> productsReceipts) {
        productsReceiptsRepository.saveAll(productsReceipts);
    }

    public <T> List<T> executeQueryAndGetResultList(String queryString, Class<T> structure) {
        return productsReceiptsRepository.executeQueryAndGetResultList(queryString, structure);
    }
}
