package com.felipeagomes.reports;

import com.felipeagomes.exceptions.IncompleteReportBuilderException;
import com.felipeagomes.reports.interfaces.ReportBuilder;

import java.nio.file.Path;

public class ExcelReportBuilder implements ReportBuilder<ExcelReportBuilder> {
    private String query;
    private String title;
    private Path resultPath;

    @Override
    public ExcelReportBuilder query(String query) {
        this.query = query;

        return this;
    }

    @Override
    public ExcelReportBuilder title(String title) {
        this.title = title;

        return this;
    }

    @Override
    public ExcelReportBuilder resultPath(Path resultPath) {
        this.resultPath = resultPath;

        return this;
    }

    @Override
    public void build() {
        if (query == null || title == null || resultPath == null) {
            throw new IncompleteReportBuilderException();
        }

//        SimpleExcelBuilder simpleExcelBuilder = new SimpleExcelBuilder();
//        simpleExcelBuilder
//                .dataRows(dataRows)
//                .title(title)



    }
}
