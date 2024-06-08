package com.felipeagomes.utils;

import com.felipeagomes.products.Product;
import com.felipeagomes.products.ProductAggregator;

import java.util.List;

public class ProductsReceiptsUtil {
    public static List<Product> aggregateProducts(List<Product> products) {
        ProductAggregator aggregator = new ProductAggregator();
        return aggregator.aggregateByCodigo(products);
    }
}
