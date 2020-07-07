package com.wk.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wk.entity.HttpResult;
import com.wk.service.HttpClientService;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HttpClientServiceImpl implements HttpClientService {

    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig config;

    @Override
    public String doGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode()==200){
            return EntityUtils.toString(response.getEntity());
        }
        return null;
    }

    @Override
    public String doGet(String url, Map<String, Object> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if(map!=null){
            for(Map.Entry<String,Object> entry:map.entrySet()){
                uriBuilder.setParameter(entry.getKey(),entry.getValue().toString());
            }
        }

        return this.doGet(uriBuilder.build().toString());
    }

    @Override
    public HttpResult doPost(String url) throws Exception {
        return this.doPost(url,null);
    }

    @Override
    public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setConfig(config);
        if(map!=null){
            System.out.println(JSONArray.toJSONString(map));
            StringEntity requestEntity = new StringEntity(JSONArray.toJSONString(map),"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(requestEntity);
        }
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    @Override
    public HttpResult doPostHead(String url, List<Map<String, Object>> list, String service, String method) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("X-Access-Token","11d852e3-306c-4c1a-95da-c25377b26972");
        httpPost.setHeader("X-Service-Id",service);
        httpPost.setHeader("X-Service-Method",method);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setConfig(config);
        if(list!=null){
            System.out.println(JSONArray.toJSONString(list));
            StringEntity requestEntity = new StringEntity(JSONArray.toJSONString(list),"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(requestEntity);
        }
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    @Override
    public HttpResult doPostHead2(String url, List<String> list, String service, String method) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("X-Access-Token","11d852e3-306c-4c1a-95da-c25377b26972");
        httpPost.setHeader("X-Service-Id",service);
        httpPost.setHeader("X-Service-Method",method);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setConfig(config);
        if(list!=null){
            StringEntity requestEntity = new StringEntity(JSONArray.toJSONString(list),"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(requestEntity);
        }
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    @Override
    public HttpResult doPostHead3(String url, List<Integer> list, String service, String method) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("X-Access-Token","11d852e3-306c-4c1a-95da-c25377b26972");
        httpPost.setHeader("X-Service-Id",service);
        httpPost.setHeader("X-Service-Method",method);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setConfig(config);
        if(list!=null){
            StringEntity requestEntity = new StringEntity(JSONArray.toJSONString(list),"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(requestEntity);
        }
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }
}
