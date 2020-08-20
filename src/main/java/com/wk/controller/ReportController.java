package com.wk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @PostMapping("/checkreport/listCheckReport")
    public String queryExamReportList(@RequestBody Map map){
        logger.info("接收到的数据是{}",map);
        System.out.println(map);

        return "{\n" +
                "\t\"code\": \"0\",\n" +
                "\t\"message\": \"处理成功\",\n" +
                "\t\"data\": {\n" +
                "\t\t\"total\": 11,\n" +
                "\t\t\"list\": [{\n" +
                "\t\t\t\"hospitalCode\": \"420104000000\",\n" +
                "\t\t\t\"hospitalName\": \"医院名称\",\n" +
                "\t\t\t\"checkId\": \"3121534\",\n" +
                "\t\t\t\"checkName\": \"胎儿一级彩超\",\n" +
                "\t\t\t\"checkTime\": \"2018-01-02 12:12:12\",\n" +
                "\t\t\t\"reportTime\": \"2018-01-02 14:12:12\",\n" +
                "\t\t\t\"examTime\": \"2018-01-02 14:12:12\",\n" +
                "\t\t\t\"departmentCode\": \"2312\",\n" +
                "\t\t\t\"departmentName\": \"妇产科\",\n" +
                "\t\t\t\"doctorCode\": \"1231243\",\n" +
                "\t\t\t\"doctorName\": \"刘医生\",\n" +
                "\t\t\t\"executeDepartmentCode\": \"23123123\",\n" +
                "\t\t\t\"executeDepartmentName\": \"执行科室名\",\n" +
                "\t\t\t\"reporter\": \"刘军\",\n" +
                "\t\t\t\"auditor\": \"审核人\",\n" +
                "\t\t\t\"checkPart\": \"检查部位\",\n" +
                "\t\t\t\"checkMethod\": \"检查方法\",\n" +
                "\t\t\t\"advice\": \"医嘱项\",\n" +
                "\t\t\t\"source\": \"1\",\n" +
                "\t\t\t\"diagnose\": \"临床诊断\",\n" +
                "\t\t\t\"patientName\": \"王飞\",\n" +
                "\t\t\t\"url\": [{\n" +
                "\t\t\t\t\"imgDt\": \"2019-11-13\",\n" +
                "\t\t\t\t\"imgUrl\": \"http://125.74.218.136:8307/report/cs1.jpg\",\n" +
                "\t\t\t\t\"imgName\": \"图片1\"\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"imgDt\": \"2019-11-15\",\n" +
                "\t\t\t\t\"imgUrl\": \"http://125.74.218.136:8307/report/cs2.jpg\",\n" +
                "\t\t\t\t\"imgName\": \"图片2\"\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"reportContents\": [{\n" +
                "\t\t\t\t\"itemName\": \"常规产前超声检查一级\",\n" +
                "\t\t\t\t\"itemContent\": \"胎位LOA。宫腔内见一胎儿回声。\"\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}\n" +
                "}";
    }

    @PostMapping("/inspectionreport/listInspectionReport")
    public String queryLabReportList(@RequestBody Map map){
        System.out.println(map);
        return "{\"code\":\"0\",\"message\":\"处理成功\",\"data\":{\"total\":12,\"list\":[{\"inspectId\":\"3121534\",\"inspectName\":\"肝功能检查\",\"inspectTime\":\"2018-01-02 12:12:12\",\"inspectStatus\":\"3\",\"reportTime\":\"2018-01-02 14:12:12\",\"examTime\":\"2018-01-02 14:12:12\",\"departmentCode\":\"2312\",\"departmentName\":\"血液科\",\"doctorCode\":\"1231243\",\"doctorName\":\"刘医生\",\"executeDepartmentCode\":\"23123123\",\"executeDepartmentName\":\"执行科室名\",\"reporter\":\"刘军\",\"auditor\":\"审核人\",\"hospitalName\":\"医院名称\",\"hospitalCode\":\"420104000000\",\"source\":\"1\",\"sampleType\":\"血液\",\"patientName\":\"李玉\",\"inspectionItems\":[{\"itemName\":\"血小板\",\"result\":\"314\",\"refRange\":\"125--350\",\"resultUnit\":\"10^9/L\",\"abnormal\":\"0\"}],\"microbiologyReports\":{\"plantResults\":[{\"plantResultNo\":\"253657653\",\"plantName\":\"铜绿假单胞菌培养\",\"plantResult\":\"一种菌\",\"resultType\":\"阳性\"}],\"antiResults\":[{\"bioNmae\":\"铜绿假单胞菌\",\"bioQuantity\":\"10000\",\"bioUnit\":\"ccu/ml\",\"expertsPrompt\":\"专家提示\",\"antiItems\":[{\"antiName\":\"头孢他啶\",\"antiMethod\":\"KB\",\"result\":\"22\",\"resultUnit\":\"结果单位\",\"antiResult\":\"耐药\"}]}]}}]}}";
    }
}
