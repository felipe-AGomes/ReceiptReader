package com.felipeagomes.products;

import com.felipeagomes.utils.NumberFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductAggregator {

    public List<Product> aggregateByCodigo(List<Product> products) {
        Map<String, List<Product>> productsGroupedByCodigo = products.stream()
                .collect(Collectors.groupingBy(Product::getCodigo));

        List<Product> productsAggregated = new ArrayList<>();

        for (List<Product> productsAgg : productsGroupedByCodigo.values()) {
            productsAggregated.add(makeProductAggregated(productsAgg));
        }

        return productsAggregated;
    }

    private Product makeProductAggregated(List<Product> productsAgg) {
        Product resultProduct = initializeProduct(productsAgg.getFirst());

        for (int i = 1; i < productsAgg.size(); i++) {
            accumulateProduct(resultProduct, productsAgg.get(i));
        }

        resultProduct.setValor(NumberFormatter.formatDoubleForDisplay(resultProduct.getValor()));
        resultProduct.setQuantidade(NumberFormatter.formatDoubleForDisplay(resultProduct.getQuantidade()));

        return resultProduct;
    }

    private Product initializeProduct(Product product) {
        Product resultProduct = new Product();
        resultProduct.setCodigo(product.getCodigo());
        resultProduct.setNome(product.getNome());
        resultProduct.setUnidade(product.getUnidade());
        resultProduct.setValor(product.getValor());
        resultProduct.setQuantidade(product.getQuantidade());

        return resultProduct;
    }

    private void accumulateProduct(Product resultProduct, Product product) {
        resultProduct.setQuantidade(resultProduct.getQuantidade() + product.getQuantidade());
    }
}
