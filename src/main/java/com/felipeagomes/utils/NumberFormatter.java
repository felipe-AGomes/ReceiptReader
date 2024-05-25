package com.felipeagomes.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

public class NumberFormatter {
    public static Double formatDoubleForDisplay(Double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.000");

        String formattedValue = decimalFormat.format(value);

        try {
            return decimalFormat.parse(formattedValue).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException("Error formatting double value", e);
        }
    }
}
