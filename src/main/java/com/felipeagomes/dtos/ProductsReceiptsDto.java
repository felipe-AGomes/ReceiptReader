package com.felipeagomes.dtos;

import java.math.BigDecimal;
import java.util.Date;

public record ProductsReceiptsDto(String code, String productName, Double quantity, BigDecimal value, String unit,
                                  Date purchaseDate, String superMarket) {
}
