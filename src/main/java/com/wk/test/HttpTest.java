package com.wk.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wk.entity.HttpResult;
import com.wk.service.HttpClientService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HttpTest {

    @Autowired
    private HttpClientService httpClientService;

    public void kengdie() throws Exception {
        List<String> request = new ArrayList<>();
        String pid = "510129000000";
        request.add(pid);
        HttpResult httpResult = httpClientService.doPostHead2("http://221.237.182.251:8888/pcn-core/*.jsonRequest",request,"pcn.pcnOrgService","queryOrgsByAreaCode");
        JSONObject jsonObject = JSONObject.parseObject(httpResult.getBody());
        JSONArray jsonArray = jsonObject.getJSONArray("body");
        for(int i =0;i<jsonArray.size();i++){
            JSONObject org = (JSONObject) jsonArray.get(i);
            String orgId = org.getString("orgId");
            String area = org.getString("area");
            String localOrgId = org.getString("localOrgId");
            Map<String,Object> map = new HashMap<>();
            map.put("orgId",orgId);
            map.put("area",area);
            map.put("localOrgId",localOrgId);
            httpClientService.doPost("http://221.237.182.251:8888/pcn-core/job/synDoctorInfoFromInterface",map);
            httpClientService.doPost("http://221.237.182.251:8888/pcn-core/job/synOrgDoctorsFromInterface",map);
            httpClientService.doPost("http://221.237.182.251:8888/pcn-core/job/synUserRoleFromInterface",map);
            httpClientService.doPost("http://221.237.182.251:8888/pcn-core/job/synBaseUser",map);
            //executor(orgId);

        }
    }

    @Test
    public void executor() throws Exception {
        String orgId = "5362f95b-984b-4849-8039-1497a9640c29";
        HttpResult doctResult = httpClientService.doPost("http://221.237.182.251:8888/pcn-core/admin/bas/doctor/list?nodeId="+orgId+"&nodeType=org&name=&localDoctorId=&page=1&length=9999&doctorName=");
        JSONArray doctArray = getArray(doctResult);
        if(doctArray!=null){
            for(int i =0;i<doctArray.size();i++){
                JSONObject doctObject = (JSONObject) doctArray.get(i);
                String phoneNo = doctObject.getString("phoneNo");
                String doctName = doctObject.getString("name");
                if(StringUtils.isEmpty(doctName)){
                    System.out.println(phoneNo);
                }
                Map<String,Object> userRequest = new HashMap<>();
                userRequest.put("pageNo",1);
                userRequest.put("pageSize",10);
                userRequest.put("loginName",phoneNo);
                userRequest.put("name","");
                List<Map<String,Object>> userRequests = new ArrayList<>();
                userRequests.add(userRequest);
                HttpResult userResult = httpClientService.doPostHead("http://221.237.182.251:8888/pcn-core/*.jsonRequest",userRequests,"pcn.pcnUserService","queryAllUsersForWeb");
                JSONArray userArray = getArray2(userResult);
                if(userArray!=null && userArray.size()>0){
                    for(int n =0;n<userArray.size();n++){
                        JSONObject userObject = (JSONObject) userArray.get(n);
                        System.out.println(JSONObject.toJSONString(userObject));
                        List<String> deleteRequests = new ArrayList<>();
                        deleteRequests.add(userObject.getString("id"));
                        httpClientService.doPostHead2("http://221.237.182.251:8888/pcn-core/*.jsonRequest",deleteRequests,"pcn.pcnUserService","deleteUser");
                    }


                }

                Map<String,Object> createRequest = new HashMap<>();
                createRequest.put("createDt","2020-05-08 11:25:06");
                createRequest.put("loginName",phoneNo);
                createRequest.put("name",doctName);
                createRequest.put("password","e10adc3949ba59abbe56e057f20f883e");
                createRequest.put("email","");
                String[] roles = {};
                createRequest.put("roles",roles);
                createRequest.put("status","1");
                List<Map<String,Object>> createRequests = new ArrayList<>();
                createRequests.add(createRequest);

                HttpResult result =httpClientService.doPostHead("http://221.237.182.251:8888/pcn-core/*.jsonRequest",createRequests,"pcn.pcnUserService","createUser");
                System.out.println(result);
            }
        }

    }

    private JSONArray getArray(HttpResult result){
        String body =result.getBody();
        JSONObject bodyJson = JSONObject.parseObject(body);
        String data = bodyJson.getString("data");
        return JSON.parseArray(data);
    }

    private JSONArray getArray2(HttpResult result){
        String body =result.getBody();
        JSONObject bodyJson = JSONObject.parseObject(body);
        JSONObject bb = bodyJson.getJSONObject("body");
        if(bb!=null){
            return bb.getJSONArray("list");
        }else{
            return null;
        }

    }
}
