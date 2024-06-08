package com.felipeagomes.receipts.interfaces;

import com.felipeagomes.products.Product;

import java.io.File;
import java.util.Date;
import java.util.List;

public interface ReceiptReader {
    ReceiptReader readReceipt(File file);
    Date getDate();
    String getCompanyName();
    List<Product> getProducts();
    void setProducts(List<Product> products);
}
