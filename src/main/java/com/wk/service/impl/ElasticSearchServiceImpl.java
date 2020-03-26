package com.wk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wk.entity.ElasticEntity;
import com.wk.service.ElasticSearchService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 根据索引名称判断索引是否已存在
     * @param indexName
     * @return
     * @throws IOException
     */
    @Override
    public boolean isExistsIndex(String indexName) throws IOException {
        return restHighLevelClient.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
    }

    /**
     * 创建索引
     * @param indexName
     * @throws IOException
     */
    @Override
    public void createIndex(String indexName) throws IOException {
        if(isExistsIndex(indexName)){
            logger.error("indexName={} 已存在，无法创建", indexName);
            return;
        }
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        //设置分片和备份
        request.settings(Settings.builder()
                .put("index.number_of_shards",3)
                .put("index.number_of_replicas",2)
                .build());

        //第一种，json字符串
        request.mapping("{\n" +
                "\t\"properties\": {\n" +
                "\t\t\"username\": {\n" +
                "\t\t\t\"type\": \"text\"\n" +
                "\t\t},\n" +
                "\t\t\"city\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}", XContentType.JSON);

        //第二种，Map
//        Map<String,Object> username = new HashMap<>();
//        username.put("type","text");
//        Map<String,Object> city = new HashMap<>();
//        city.put("type","keyword");
//        Map<String,Object> properties = new HashMap<>();
//        properties.put("username",username);
//        properties.put("city",city);
//        Map<String,Object> mapping = new HashMap<>();
//        mapping.put("properties",properties);
//        request.mapping(mapping);

        //第三种，XContentBuilder
//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject();
//        {
//            builder.startObject("properties");
//            {
//                builder.startObject("username");
//                {
//                    builder.field("type","text");
//                }
//                builder.endObject();
//
//                builder.startObject("city");
//                {
//                    builder.field("type","keyword");
//                }
//                builder.endObject();
//            }
//            builder.endObject();
//        }
//        builder.endObject();
//

        restHighLevelClient.indices().create(request,RequestOptions.DEFAULT);

    }

    /**
     * 删除索引
     * @param indexName
     * @throws IOException
     */
    @Override
    public void deleteIndex(String indexName) throws IOException {
        if(!isExistsIndex(indexName)){
            logger.error("indexName={} 索引不存在", indexName);
            return;
        }
        restHighLevelClient.indices().delete(new DeleteIndexRequest(indexName),RequestOptions.DEFAULT);
    }

    /**
     * 新增或更新文档
     * @param indexName
     * @param elasticEntity
     * @throws IOException
     */
    @Override
    public void insertOrUpdateDocument(String indexName, ElasticEntity elasticEntity) throws IOException {
        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.id(elasticEntity.getId());
        indexRequest.source(JSONObject.toJSONString(elasticEntity.getData()),XContentType.JSON);
        restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
    }

    /**
     * 根据ID删除文档
     * @param indexName
     * @param id
     * @throws IOException
     */
    @Override
    public void deleteDocumentById(String indexName, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(indexName,id);
        restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
    }

    /**
     * 条件查询
     * @param indexName
     * @param builder
     * @param c
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> List<T> searchDocument(String indexName, SearchSourceBuilder builder, Class<T> c) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(builder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        List<T> res = new ArrayList<>();
        for(SearchHit searchHit:searchHits){
            res.add(JSONObject.parseObject(searchHit.getSourceAsString(),c));
        }
        return res;
    }

    /**
     * 高亮条件查询
     * @param indexName
     * @param builder
     * @param c
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> List<T> searchHighlightDocument(String indexName, SearchSourceBuilder builder, Class<T> c) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(builder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        List<T> res = new ArrayList<>();
        for(SearchHit searchHit:searchHits){
            //返回结果转换成Map
            Map<String,Object> sourceMap = searchHit.getSourceAsMap();
            //获取高亮的返回结果
            Map<String, HighlightField> map = searchHit.getHighlightFields();
            //循环设置的高亮字段
            for(Map.Entry<String, HighlightField> entry:map.entrySet()){
                //将高亮字段格式替换原结果中的值
                sourceMap.put(entry.getKey(),entry.getValue().getFragments()[0].toString());
            }
            res.add(JSONObject.parseObject(JSONObject.toJSONString(sourceMap),c));
        }
        return res;
    }

    @Override
    public void deleteDocumentByQuery(String indexName) throws IOException {
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(indexName);
        deleteByQueryRequest.setQuery(new MatchQueryBuilder("username","张三"));
        restHighLevelClient.deleteByQuery(deleteByQueryRequest,RequestOptions.DEFAULT);
    }

    /**
     * 批量新增
     * @param indexName
     * @param elasticEntities
     * @throws IOException
     */
    @Override
    public void insertDocumentBatch(String indexName, List<ElasticEntity> elasticEntities) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        elasticEntities.forEach(item ->{
            bulkRequest.add(new IndexRequest(indexName).id(item.getId()).source(JSONObject.toJSONString(item.getData()),XContentType.JSON));
        });
        restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
    }

    /**
     * 批量删除文档
     * @param indexName
     * @param idList
     * @param <T>
     * @throws IOException
     */
    public <T> void deleteDocumentBatch(String indexName, Collection<T> idList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        idList.forEach(item ->{
            bulkRequest.add(new DeleteRequest(indexName, item.toString()));
        });
        restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);

    }

}
