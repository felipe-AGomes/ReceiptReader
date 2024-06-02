package com.felipeagomes.reports.interfaces;

import java.nio.file.Path;

public interface ReportBuilder <T extends ReportBuilder<T>> {
    T query(String query);
    T title(String title);
    T resultPath(Path resultPath);
    void build();
}
