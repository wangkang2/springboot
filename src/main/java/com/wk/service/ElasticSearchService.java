package com.wk.service;

import com.wk.entity.ElasticEntity;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;

public interface ElasticSearchService {

    boolean isExistsIndex(String indexName) throws IOException;

    void createIndex(String indexName) throws IOException;

    void deleteIndex(String indexName) throws IOException;

    void insertOrUpdateDocument(String indexName, ElasticEntity elasticEntity) throws IOException;

    void deleteDocumentById(String indexName,String id) throws IOException;

    <T> List<T> searchDocument(String indexName, SearchSourceBuilder builder, Class<T> c) throws IOException;

    <T> List<T> searchHighlightDocument(String indexName, SearchSourceBuilder builder, Class<T> c) throws IOException;

    void deleteDocumentByQuery(String indexName) throws IOException;

    void insertDocumentBatch(String indexName, List<ElasticEntity> elasticEntities) throws IOException;
}
