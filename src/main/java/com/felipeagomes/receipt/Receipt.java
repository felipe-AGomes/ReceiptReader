package com.felipeagomes.receipt;

import com.felipeagomes.products.Product;

import java.util.List;

public record Receipt (List<Product> products){};
