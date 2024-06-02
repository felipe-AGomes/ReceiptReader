package com.felipeagomes.dtos;

import com.felipeagomes.dtos.interfaces.ProductsReceiptsDtoInterface;

import java.math.BigDecimal;
import java.util.Date;

public record ProductsReceiptsWithTotValueDto(
        String code,
        String productName,
        Double quantity,
        BigDecimal value,
        BigDecimal totValue,
        String unit,
        Date purchaseDate,
        String superMarket) implements ProductsReceiptsDtoInterface {
}
