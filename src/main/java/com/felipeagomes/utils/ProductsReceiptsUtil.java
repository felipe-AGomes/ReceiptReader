package com.felipeagomes.utils;

import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.products.Product;
import com.felipeagomes.products.ProductAggregator;
import com.felipeagomes.receipts.interfaces.ReceiptReader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductsReceiptsUtil {
    public static List<Product> aggregateProducts(List<Product> products) {
        ProductAggregator aggregator = new ProductAggregator();
        return aggregator.aggregateByCodigo(products);
    }
}
