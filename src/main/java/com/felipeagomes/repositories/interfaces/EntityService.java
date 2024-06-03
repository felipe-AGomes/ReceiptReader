package com.felipeagomes.repositories.interfaces;

import com.felipeagomes.dtos.interfaces.ProductsReceiptsDtoInterface;
import com.felipeagomes.reports.excel.DataRow;

import java.util.List;

public interface EntityService {
    <T> List<T> executeQueryAndGetResultList(String queryString, Class<T> structure);
    <T> DataRow toDataRow(T data);
}
