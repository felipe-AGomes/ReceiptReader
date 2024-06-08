package com.felipeagomes.services;

import com.felipeagomes.dtos.ProductsReceiptsDto;
import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.mappers.ProductsReceiptsMapper;
import com.felipeagomes.reports.excel.DataRow;
import com.felipeagomes.repositories.ProductsReceiptsRepository;
import com.felipeagomes.repositories.interfaces.EntityService;

import java.util.List;

public class ProductsReceiptsService implements EntityService {
    private final ProductsReceiptsRepository productsReceiptsRepository;
    private final ProductsReceiptsMapper productsReceiptsMapper;

    public ProductsReceiptsService(ProductsReceiptsRepository productsReceiptsRepository, ProductsReceiptsMapper productsReceiptsMapper) {
        this.productsReceiptsRepository = productsReceiptsRepository;
        this.productsReceiptsMapper = productsReceiptsMapper;
    }

    public void saveAllProductsReceipts(List<ProductsReceipts> productsReceipts) {
        productsReceiptsRepository.saveAll(productsReceipts);
    }

    public <T> List<T> executeQueryAndGetResultList(String queryString, Class<T> structure) {
        List<ProductsReceiptsDto> productsReceiptsDto = productsReceiptsRepository.executeQueryAndGetResultList(queryString);

        return productsReceiptsDto.stream().map(product -> productsReceiptsMapper.toDto(product, structure)).toList();
    }

    @Override
    public <T> DataRow toDataRow(T data) {
        return productsReceiptsMapper.toDataRow(data);
    }
}
