package com.felipeagomes.mappers;

import com.felipeagomes.reports.excel.DataRow;

public interface MapperService<T> {
    DataRow toDataRow(T data);
}
