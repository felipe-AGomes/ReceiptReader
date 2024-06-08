package com.felipeagomes.reports.excel;

import com.felipeagomes.exceptions.IncompleteReportBuilderException;
import com.felipeagomes.reports.interfaces.ReportBuilder;
import com.felipeagomes.repositories.interfaces.EntityService;

import java.nio.file.Path;
import java.util.List;

public class ExcelReportBuilder<S> implements ReportBuilder<S, ExcelReportBuilder<S>> {
    final private EntityService entityService;
    private String query;
    private String title;
    private Path resultPath;
    private Class<S> structure;

    public ExcelReportBuilder(EntityService entityService) {
        this.entityService = entityService;
    }

    @Override
    public ExcelReportBuilder<S> query(String query) {
        this.query = query;

        return this;
    }

    @Override
    public ExcelReportBuilder<S> title(String title) {
        this.title = title;

        return this;
    }

    @Override
    public ExcelReportBuilder<S> resultPath(Path resultPath) {
        this.resultPath = resultPath;

        return this;
    }

    @Override
    public ExcelReportBuilder<S> structure(Class<S> structure) {
        this.structure = structure;

        return this;
    }

    @Override
    public void build() {
        if (query == null || title == null || resultPath == null || structure == null) {
            throw new IncompleteReportBuilderException("Query, title, resultPath, and structure must all be set before building the report.");
        }

        List<S> dtos = executeQueryAndGetResultList(query, structure);

        List<DataRow> dataRows = dtos.stream().map(entityService::toDataRow).toList();

        SimpleExcelBuilder simpleExcelBuilder = new SimpleExcelBuilder();
        simpleExcelBuilder
                .dataRows(dataRows)
                .title(title)
                .resultPath(resultPath)
                .build();
    }

    private <T> List<T> executeQueryAndGetResultList(String namedQuery, Class<T> structure) {
        return entityService.executeQueryAndGetResultList(namedQuery, structure);
    }
}
