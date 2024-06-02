package com.felipeagomes.repositories.interfaces;

import java.util.List;

public interface QueryService {
    <T> List<T> executeQueryAndGetResultList(String queryString, Class<T> structure);
}
