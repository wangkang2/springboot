package com.wk.service;

import com.wk.entity.HttpResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface HttpClientService {

    String doGet(String url) throws IOException, Exception;
    String doGet(String url,Map<String,Object> map) throws URISyntaxException, Exception;
    HttpResult doPost(String url) throws Exception;
    HttpResult doPost(String url, Map<String,Object> map) throws UnsupportedEncodingException, Exception;

    HttpResult doPostHead(String url, List<Map<String, Object>> list, String service, String method) throws Exception;

    HttpResult doPostHead2(String url, List<String> list, String service, String method) throws Exception;

    HttpResult doPostHead3(String url, List<Integer> list, String service, String method) throws Exception;

}
