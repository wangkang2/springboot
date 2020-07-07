package com.wk.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wk.entity.HttpResult;
import com.wk.service.HttpClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HttpTest3 {

    @Autowired
    private HttpClientService httpClientService;

    @Test
    public void batchExecutor() throws Exception {
        List<String> teamList = new ArrayList<>();
        teamList.add("1649");
        teamList.add("1759");
//        teamList.add("1760");
//        teamList.add("1761");
//        teamList.add("1762");
//        teamList.add("1763");
//        teamList.add("1764");
//        teamList.add("1765");
//        teamList.add("1766");
//        teamList.add("1767");
//        teamList.add("1768");
//        teamList.add("1769");
//        teamList.add("1828");

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
                    pack.put("serviceId",7920);
                    pack.put("signPackId",0);
                    pack.put("spPackId",7920);
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
                    pack2.put("serviceId",7919);
                    pack2.put("signPackId",0);
                    pack2.put("spPackId",7919);
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
                    pack3.put("serviceId",7917);
                    pack3.put("signPackId",0);
                    pack3.put("spPackId",7917);
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
                    pack4.put("serviceId",7918);
                    pack4.put("signPackId",0);
                    pack4.put("spPackId",7918);
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
                    pack5.put("serviceId",7922);
                    pack5.put("signPackId",0);
                    pack5.put("spPackId",7922);
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
                    pack6.put("serviceId",7921);
                    pack6.put("signPackId",0);
                    pack6.put("spPackId",7921);
                    pack6.put("spPackName","严重精神障碍患者服务包");
                    pack6.put("suitableObject","12");
                    packs.add(pack6);
                }
                if(personGroup.indexOf("02")!=-1){
                    Date dob = signInfo.getDate("dob");
                    if(dob!=null){
                        int age = getAgeByBirth(dob);
                        if(age>3){
                            Map<String,Object> pack7 = new HashMap<>();
                            pack7.put("applyId",0);
                            pack7.put("aserviceId",0);
                            pack7.put("check",true);
                            pack7.put("price","0");
                            pack7.put("serviceId",7915);
                            pack7.put("signPackId",0);
                            pack7.put("spPackId",7915);
                            pack7.put("spPackName","4-6岁儿童服务包");
                            pack7.put("suitableObject","01,02,03,04");
                            packs.add(pack7);
                        }else {
                            Map<String,Object> pack8 = new HashMap<>();
                            pack8.put("applyId",0);
                            pack8.put("aserviceId",0);
                            pack8.put("check",true);
                            pack8.put("price","0");
                            pack8.put("serviceId",7956);
                            pack8.put("signPackId",0);
                            pack8.put("spPackId",7956);
                            pack8.put("spPackName","0-3岁儿童服务包");
                            pack8.put("suitableObject","01,02,03,04");
                            packs.add(pack8);
                        }
                    }else{
                        Map<String,Object> pack9 = new HashMap<>();
                        pack9.put("applyId",0);
                        pack9.put("aserviceId",0);
                        pack9.put("check",true);
                        pack9.put("price","0");
                        pack9.put("serviceId",7915);
                        pack9.put("signPackId",0);
                        pack9.put("spPackId",7915);
                        pack9.put("spPackName","4-6岁儿童服务包");
                        pack9.put("suitableObject","01,02,03,04");
                        packs.add(pack9);
                    }

                }

            }else{


                Map<String,Object> pack = new HashMap<>();
                pack.put("applyId",0);
                pack.put("aserviceId",0);
                pack.put("check",true);
                pack.put("price","0");
                pack.put("serviceId",7916);
                pack.put("signPackId",0);
                pack.put("spPackId",7916);
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

    private static int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }

}
