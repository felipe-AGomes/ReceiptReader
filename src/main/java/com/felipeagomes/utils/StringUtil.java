package com.felipeagomes.utils;

public class StringUtil {
    public static String formatCompanyName(String companyName) {
        return companyName
                .replaceAll("\\d", "")
                .replaceAll(" ", "_")
                .replaceAll("S/A", "")
                .replaceAll("[/-]", "")
                .replaceAll("__", "_");
    }
}
