package com.felipeagomes.exceptions;

public class IncompleteReportBuilderException extends ReceiptReaderException {
    public IncompleteReportBuilderException() {
        super("Query, title, and resultPath must all be set before building the report.");
    }
}
