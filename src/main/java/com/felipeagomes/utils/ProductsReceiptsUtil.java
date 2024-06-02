package com.felipeagomes.utils;

import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.products.Product;
import com.felipeagomes.products.ProductAggregator;
import com.felipeagomes.receipt.ReceiptReader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductsReceiptsUtil {
    public static List<Product> aggregateProducts(ReceiptReader receiptReader) {
        ProductAggregator aggregator = new ProductAggregator();
        List<Product> products = receiptReader.getProducts();
        return aggregator.aggregateByCodigo(products);
    }

    public static List<ProductsReceipts> receiptReaderToProductsReceipts(ReceiptReader receiptReader) {
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
}
