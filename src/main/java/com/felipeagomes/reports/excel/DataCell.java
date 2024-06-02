package com.felipeagomes.reports.excel;

import org.apache.poi.ss.usermodel.CellType;

public record DataCell(Object cellValue, CellType cellType, String columnName) {
}
