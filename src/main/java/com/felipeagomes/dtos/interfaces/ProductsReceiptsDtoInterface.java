package com.felipeagomes.dtos.interfaces;

import java.math.BigDecimal;
import java.util.Date;

public interface ProductsReceiptsDtoInterface {
    String code();

    String productName();

    Double quantity();

    BigDecimal value();

    String unit();

    Date purchaseDate();

    String superMarket();
}
