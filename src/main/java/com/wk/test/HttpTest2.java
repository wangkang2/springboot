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
public class HttpTest2 {

    @Autowired
    private HttpClientService httpClientService;

    @Test
    public void batchExecutor() throws Exception {
        List<String> teamList = new ArrayList<>();
        teamList.add("1573");
        teamList.add("1574");
        teamList.add("1575");
        teamList.add("1576");
//        teamList.add("1555");



//        teamList.add("80");
//        teamList.add("83");
//        teamList.add("84");
//        teamList.add("85");
//        teamList.add("87");
//        teamList.add("88");
//        teamList.add("89");
//        teamList.add("90");
//        teamList.add("637");
//        teamList.add("639");
//        teamList.add("1900");
//        teamList.add("2240");
//        teamList.add("2269");
        for(String teamId:teamList){
            executor(teamId);
        }
    }

    //@Test
    public void executor(String teamId) throws Exception {
        List<String> teamList = new ArrayList<>();
        teamList.add(teamId);
        HttpResult pops = httpClientService.doPostHead2("http://221.237.182.251:8888/pcn-core/*.jsonRequest",teamList,"pcn.myResidentDoctorService","querySignResidentRecord");
        JSONObject result = JSONObject.parseObject(pops.getBody());
        JSONObject body = result.getJSONObject("body");
        JSONArray signResidents = body.getJSONArray("SignResident");
        for(int i =0;i<signResidents.size();i++){
            JSONObject signResident = (JSONObject) signResidents.get(i);
            int signId = signResident.getInteger("signId");
            List<Integer> signList = new ArrayList<>();
            signList.add(signId);
            HttpResult siss = httpClientService.doPostHead3("http://221.237.182.251:8888/pcn-core/*.jsonRequest",signList,"pcn.residentSignService","getSignRecordDetailBySignId");
            JSONObject resultSiss = JSONObject.parseObject(siss.getBody());
            JSONObject sissBody = resultSiss.getJSONObject("body");
            JSONArray sissArray = sissBody.getJSONArray("signPackageInfo");
            if(sissArray==null || sissArray.size() ==0){
                List<Map<String,Object>> requests = new ArrayList<>();
                Map<String,Object> request = new HashMap<>();
                request.put("cancelStatus",sissBody.getString("cancelStatus"));
                request.put("signDate",sissBody.getString("signDate"));
                JSONObject signInfo = sissBody.getJSONObject("SignInfo");
                String personGroup = signInfo.getString("personGroup");
                signInfo.put("status",0);
                signInfo.put("signFrom","11");
                signInfo.put("signId",signId);
                request.put("signInfo",signInfo);
                List<Map<String,Object>> packs = new ArrayList<>();
                if(personGroup.indexOf("01")!=-1 || personGroup.indexOf("02")!=-1 ||personGroup.indexOf("03")!=-1 || personGroup.indexOf("04")!=-1 ||
                        personGroup.indexOf("05")!=-1 || personGroup.indexOf("06")!=-1 || personGroup.indexOf("07")!=-1){
                    if(personGroup.indexOf("01")!=-1){
                        Map<String,Object> pack = new HashMap<>();
                        pack.put("applyId",0);
                        pack.put("aserviceId",0);
                        pack.put("check",true);
                        pack.put("price","0");
                        pack.put("serviceId",6287);
                        pack.put("signPackId",0);
                        pack.put("spPackId",6287);
                        pack.put("spPackName","孕产妇服务包");
                        pack.put("suitableObject","05");
                        packs.add(pack);
                    }

                    if(personGroup.indexOf("03")!=-1){
                        Map<String,Object> pack2 = new HashMap<>();
                        pack2.put("applyId",0);
                        pack2.put("aserviceId",0);
                        pack2.put("check",true);
                        pack2.put("price","0");
                        pack2.put("serviceId",6286);
                        pack2.put("signPackId",0);
                        pack2.put("spPackId",6286);
                        pack2.put("spPackName","老年人服务包");
                        pack2.put("suitableObject","07");
                        packs.add(pack2);
                    }

                    if(personGroup.indexOf("04")!=-1){
                        Map<String,Object> pack3 = new HashMap<>();
                        pack3.put("applyId",0);
                        pack3.put("aserviceId",0);
                        pack3.put("check",true);
                        pack3.put("price","0");
                        pack3.put("serviceId",6282);
                        pack3.put("signPackId",0);
                        pack3.put("spPackId",6282);
                        pack3.put("spPackName","高血压服务包");
                        pack3.put("suitableObject","08,09");
                        packs.add(pack3);
                    }

                    if(personGroup.indexOf("05")!=-1){
                        Map<String,Object> pack4 = new HashMap<>();
                        pack4.put("applyId",0);
                        pack4.put("aserviceId",0);
                        pack4.put("check",true);
                        pack4.put("price","0");
                        pack4.put("serviceId",6284);
                        pack4.put("signPackId",0);
                        pack4.put("spPackId",6284);
                        pack4.put("spPackName","糖尿病服务包");
                        pack4.put("suitableObject","10,11");
                        packs.add(pack4);
                    }
                    if(personGroup.indexOf("07")!=-1){
                        Map<String,Object> pack5 = new HashMap<>();
                        pack5.put("applyId",0);
                        pack5.put("aserviceId",0);
                        pack5.put("check",true);
                        pack5.put("price","0");
                        pack5.put("serviceId",9491);
                        pack5.put("signPackId",0);
                        pack5.put("spPackId",9491);
                        pack5.put("spPackName","结核病患者服务包");
                        pack5.put("suitableObject","13");
                        packs.add(pack5);
                    }
                    if(personGroup.indexOf("06")!=-1){
                        Map<String,Object> pack6 = new HashMap<>();
                        pack6.put("applyId",0);
                        pack6.put("aserviceId",0);
                        pack6.put("check",true);
                        pack6.put("price","0");
                        pack6.put("serviceId",6288);
                        pack6.put("signPackId",0);
                        pack6.put("spPackId",6288);
                        pack6.put("spPackName","严重精神障碍患者服务包");
                        pack6.put("suitableObject","12");
                        packs.add(pack6);
                    }
                    if(personGroup.indexOf("02")!=-1){
                        Map<String,Object> pack7 = new HashMap<>();
                        pack7.put("applyId",0);
                        pack7.put("aserviceId",0);
                        pack7.put("check",true);
                        pack7.put("price","0");
                        pack7.put("serviceId",6278);
                        pack7.put("signPackId",0);
                        pack7.put("spPackId",6278);
                        pack7.put("spPackName","4-6岁儿童服务包");
                        pack7.put("suitableObject","01,02,03,04");
                        packs.add(pack7);
                    }

                }else{
                    Map<String,Object> pack = new HashMap<>();
                    pack.put("applyId",0);
                    pack.put("aserviceId",0);
                    pack.put("check",true);
                    pack.put("price","0");
                    pack.put("serviceId",6280);
                    pack.put("signPackId",0);
                    pack.put("spPackId",6280);
                    pack.put("spPackName","普通人群服务包");
                    pack.put("suitableObject","01,02,03,04");
                    packs.add(pack);
                }


                request.put("signPackageInfo",packs);
                requests.add(request);
                HttpResult endss = httpClientService.doPostHead("http://221.237.182.251:8888/pcn-core/*.jsonRequest",requests,"pcn.myResidentDoctorService","updatePeopleByMpiId");
                System.out.println(endss.getBody());
            }

        }

    }

}
