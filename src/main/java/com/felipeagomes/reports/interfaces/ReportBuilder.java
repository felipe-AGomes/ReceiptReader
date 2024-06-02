package com.felipeagomes.reports.interfaces;

import java.nio.file.Path;

public interface ReportBuilder <S, T extends ReportBuilder<S, T>> {
    T query(String query);
    T title(String title);
    T resultPath(Path resultPath);
    T structure(Class<S> structure);
    void build();
}
