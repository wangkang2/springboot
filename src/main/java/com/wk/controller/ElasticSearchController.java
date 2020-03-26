package com.wk.controller;

import com.wk.entity.ElasticEntity;
import com.wk.entity.EsTestEntity;
import com.wk.service.ElasticSearchService;
import com.wk.utils.BeanUtil;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/es")
public class ElasticSearchController {

    @Autowired
    private ElasticSearchService elasticSearchServiceImpl;

    @GetMapping("isExistsIndex/{indexName}")
    public boolean isExistsIndex(@PathVariable(value = "indexName") String indexName){
        try {
            return elasticSearchServiceImpl.isExistsIndex(indexName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @GetMapping("createIndex/{indexName}")
    public void createIndex(@PathVariable(value = "indexName") String indexName){
        try {
            elasticSearchServiceImpl.createIndex(indexName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("deleteIndex/{indexName}")
    public void deleteIndex(@PathVariable String indexName){
        try {
            elasticSearchServiceImpl.deleteIndex(indexName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("insertOrUpdateDocument/{indexName}")
    public Map<String,String> insertOrUpdateDocument(@PathVariable String indexName,@RequestBody EsTestEntity esTestEntity){
        ElasticEntity<EsTestEntity> entity = new ElasticEntity<>();
        //注册生成UUID插入实体类的ID
        if(StringUtils.isEmpty(esTestEntity.getId())){
            String id = UUID.randomUUID().toString();
            entity.setId(id);
            esTestEntity.setId(id);
        }
        entity.setId(esTestEntity.getId());
        entity.setData(esTestEntity);
        try {
            elasticSearchServiceImpl.insertOrUpdateDocument(indexName,entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("searchDocument/{indexName}")
    public List<EsTestEntity> searchDocument(@PathVariable String indexName, @RequestBody EsTestEntity esTestEntity){
        try {
            SearchSourceBuilder builder = new SearchSourceBuilder();
            Map<String,Object> requestMap = BeanUtil.convertToMap(esTestEntity);
            for(Map.Entry<String,Object> entry:requestMap.entrySet()){
                builder.query(QueryBuilders.matchQuery(entry.getKey(),entry.getValue()));
            }
            //分页
            builder.from(0);
            builder.size(5);
            //排序
            builder.sort("city", SortOrder.ASC);
            //设置超时时间
            builder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            return elasticSearchServiceImpl.searchDocument(indexName,builder,EsTestEntity.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("searchHighlightDocument/{indexName}")
    public List<EsTestEntity> searchHighlightDocument(@PathVariable String indexName, @RequestBody EsTestEntity esTestEntity){
        try {
            SearchSourceBuilder builder = new SearchSourceBuilder();
            Map<String,Object> requestMap = BeanUtil.convertToMap(esTestEntity);
            for(Map.Entry<String,Object> entry:requestMap.entrySet()){
                builder.query(QueryBuilders.matchQuery(entry.getKey(),entry.getValue()));
            }
            builder.from(0);
            builder.size(5);
            builder.sort("city", SortOrder.ASC);
            builder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags("<front color = 'red'>");
            highlightBuilder.postTags("</front>");
            HighlightBuilder.Field field = new HighlightBuilder.Field("username");
            highlightBuilder.field(field);
            builder.highlighter(highlightBuilder);

            return elasticSearchServiceImpl.searchHighlightDocument(indexName,builder,EsTestEntity.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("deleteDocumentById/{indexName}/{id}")
    public void deleteDocumentById(@PathVariable String indexName, @PathVariable String id){
        try {
            elasticSearchServiceImpl.deleteDocumentById(indexName,id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
