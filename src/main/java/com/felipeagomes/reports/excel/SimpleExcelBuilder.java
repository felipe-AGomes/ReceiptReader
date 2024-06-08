package com.felipeagomes.reports.excel;

import com.felipeagomes.exceptions.IncompleteSimpleExcelBuilderException;
import com.felipeagomes.exceptions.InvalidTypeSimpleExcelBuilderException;
import com.felipeagomes.exceptions.UnsupportedCellTypeSimpleExcelBuilderException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SimpleExcelBuilder {
    final private Integer HEADER_POSITION = 0;

    private List<DataRow> dataRows;
    private String title;
    private Path resultPath;

    public SimpleExcelBuilder dataRows(List<DataRow> dataRows) {
        this.dataRows = dataRows;

        return this;
    }

    public SimpleExcelBuilder title(String title) {
        this.title = title;

        return this;
    }

    public SimpleExcelBuilder resultPath(Path resultPath) {
        this.resultPath = resultPath;

        return this;
    }

    public void build() {
        final String DEFAULT_TITLE = "relatório";
        final String EXCEL_EXTENSION = ".xls";

        if (dataRows == null || resultPath == null) {
            throw new IncompleteSimpleExcelBuilderException("dataRows and resultPath must be defined before build method");
        }

        if (title == null) {
            title = DEFAULT_TITLE;
        }
        Path fullResultPath = resultPath.resolve(title + EXCEL_EXTENSION);

        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(title);
            createHeader(sheet);
            createBody(sheet);

            workbook.write(fullResultPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createBody(Sheet sheet) {
        for (int i = 0; i < dataRows.size(); i++) {
            DataRow dataRow = dataRows.get(i);
            Row row = sheet.createRow(i + HEADER_POSITION);

            for (int j = 0; j < dataRow.dataCells().size(); j++) {
                DataCell dataCell = dataRow.dataCells().get(j);
                createCellBody(row, dataCell, j);
            }
        }
    }

    private void createCellBody(Row row, DataCell dataCell, int index) {
        CellType cellType = dataCell.cellType();
        Object cellValue = dataCell.cellValue();

        Cell cell = row.createCell(index, cellType);

        switch (cellType) {
            case NUMERIC -> setNumericCellValue(cell, cellValue);
            case STRING -> setStringCellValue(cell, cellValue);
            default -> throw new UnsupportedCellTypeSimpleExcelBuilderException("Unsupported cellType: " + cellType);
        }
    }

    private void setNumericCellValue(Cell cell, Object cellValue) {
        switch (cellValue) {
            case Double d -> cell.setCellValue(d);
            case BigDecimal b -> cell.setCellValue(b.doubleValue());
            case Long l -> cell.setCellValue(l);
            case Integer i -> cell.setCellValue(i);
            default ->
                    throw new InvalidTypeSimpleExcelBuilderException("Invalid cellValue type for CellType.NUMERIC: " + cellValue.getClass().getName());
        }
    }

    private void setStringCellValue(Cell cell, Object cellValue) {
        if (cellValue instanceof String) {
            cell.setCellValue((String) cellValue);
        } else if (cellValue instanceof Timestamp timestamp) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            cell.setCellValue(timestamp.toLocalDateTime().format(dtf));
        } else {
            throw new InvalidTypeSimpleExcelBuilderException("Invalid cellValue type for CellType.NUMERIC: " + cellValue.getClass().getName());
        }
    }

    private void createHeader(Sheet sheet) {
        List<DataCell> dataCells = dataRows.getFirst().dataCells();
        Row header = sheet.createRow(HEADER_POSITION);

        for (int i = 0; i < dataCells.size(); i++) {
            DataCell dataCell = dataCells.get(i);
            createCellHeader(header, dataCell, i);
        }
    }

    private void createCellHeader(Row header, DataCell dataCell, int index) {
        Cell cell = header.createCell(index, CellType.STRING);

        cell.setCellValue(dataCell.columnName());
    }
}
