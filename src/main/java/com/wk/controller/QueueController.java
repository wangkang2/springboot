package com.wk.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QueueController {

    @PostMapping("/queues/listQueues")
    public String listQueues(@RequestBody Map map){
        System.out.println(map);
        return "{\"code\": 200,\"message\":\"\",\"data\": [{\"departmentName\": \"五官科\",\"departmentLocation\": \"\",\"guidanceInformation\": \"\",\"tips\": \"\",\"remarks\": \"\",\"doctorName\": \"吴月智\",\"serialNumber\": \"06Y\",\"currentNumber\": \"01\",\"frontNumber\": \"56\",\"avgTime\": 5,\"waitingNumber\": 3,\"expectTime\": \"\",\"queuesUpdateTime\": \"\",\"patientName\": \"叶天天\",\"patientMedicalCardNumber\": \"\",\"patientMedicalCardType\": 1}]}";
    }


    @PostMapping("/queues/listWholeQueues")
    public String listWholeQueues(@RequestBody Map map){
        System.out.println(map);
        return "{\"code\":\"1\",\"message\":\"成功\",\"data\":{\"total\":4,\"list\":[{\"departmentName\":\"内科\",\"departmentLocation\":\"wefwede\",\"listQueues\":[{\"currentNumber\":\"123\",\"queueName\":\"张医生专家\",\"doctorType\":\"1\",\"waitingNumber\":\"23\",\"remainingNumber\":\"32\"},{\"currentNumber\":\"123\",\"queueName\":\"李医生\",\"doctorType\":\"2\",\"waitingNumber\":\"23\",\"remainingNumber\":\"32\"}]},{\"departmentName\":\"外科\",\"departmentLocation\":\"wefwede\",\"listQueues\":[{\"currentNumber\":\"123\",\"queueName\":\"ere\",\"doctorType\":\"1\",\"waitingNumber\":\"23\",\"remainingNumber\":\"32\"}]}]}}";
    }

    @PostMapping("/takenumber/listTakeNumber")
    public String listTakeNumber(@RequestBody Map map){
        System.out.println(map);
        return "{\"code\":200,\"message\":\"处理成功\",\"data\":[{\"departmentId\":\"104\",\"departmentName\":\"消化科\",\"doctorId\":\"1000005\",\"doctorName\":\"林樱\",\"scheduleDate\":\"2019-10-30 00:00:00\",\"beginTime\":\"2019-10-30 08:30:00\",\"endTime\":\"2019-10-30 08:35:00\",\"orderId\":\"220\",\"orderStatus\":\"4\",\"payStatus\":\"1\",\"registerTime\":\"2019-10-30 22:19:44\",\"organizationId\":\"\",\"organizationName\":\"\",\"majorQualify\":\"主治医师\",\"outOrderNumber\":null,\"patientName\":\"张伟\",\"patientId\":\"1257\",\"regFee\":\"0.00\"},{\"departmentId\":\"104\",\"departmentName\":\"消化科\",\"doctorId\":\"1000005\",\"doctorName\":\"林樱\",\"scheduleDate\":\"2019-10-30 00:00:00\",\"beginTime\":\"2019-10-30 09:00:00\",\"endTime\":\"2019-10-30 09:05:00\",\"orderId\":\"195\",\"orderStatus\":\"1\",\"payStatus\":\"2\",\"registerTime\":\"2019-07-12 13:44:14\",\"organizationId\":\"\",\"organizationName\":\"\",\"majorQualify\":\"主治医师\",\"outOrderNumber\":null,\"patientName\":\"张伟\",\"patientId\":\"1257\",\"regFee\":\"0.00\"}]}";
    }

    @PostMapping("/takenumber/takenumber")
    public String takenumber(@RequestBody Map map){
        System.out.println(map);
        return "{\"code\":200,\"message\":\"处理成功\",\"data\":[{\"departmentId\":\"104\",\"departmentName\":\"消化科\",\"doctorId\":\"1000005\",\"doctorName\":\"林樱\",\"scheduleDate\":\"2019-10-30 00:00:00\",\"beginTime\":\"2019-10-30 08:30:00\",\"endTime\":\"2019-10-30 08:35:00\",\"orderId\":\"220\",\"orderStatus\":\"4\",\"payStatus\":\"1\",\"registerTime\":\"2019-10-30 22:19:44\",\"organizationId\":\"\",\"organizationName\":\"\",\"majorQualify\":\"主治医师\",\"outOrderNumber\":null,\"patientName\":\"张伟\",\"patientId\":\"1257\",\"regFee\":\"0.00\"},{\"departmentId\":\"104\",\"departmentName\":\"消化科\",\"doctorId\":\"1000005\",\"doctorName\":\"林樱\",\"scheduleDate\":\"2019-10-30 00:00:00\",\"beginTime\":\"2019-10-30 09:00:00\",\"endTime\":\"2019-10-30 09:05:00\",\"orderId\":\"195\",\"orderStatus\":\"1\",\"payStatus\":\"2\",\"registerTime\":\"2019-07-12 13:44:14\",\"organizationId\":\"\",\"organizationName\":\"\",\"majorQualify\":\"主治医师\",\"outOrderNumber\":null,\"patientName\":\"张伟\",\"patientId\":\"1257\",\"regFee\":\"0.00\"}]}";
    }

    @PostMapping("/takenumber/getRegistrationForm")
    public String getRegistrationForm(@RequestBody Map map){
        System.out.println(map);
        return "{\"code\":200,\"message\":\"请求成功\",\"data\":{\"patientName\":\"张条码\",\"patientSex\":\"1\",\"patientId\":\"139\",\"departmentId\":null,\"departmentName\":null,\"doctorId\":null,\"doctorName\":null,\"scheduleDate\":null,\"beginTime\":null,\"endTime\":null,\"registerTime\":1495178623597,\"organizationId\":\"1\",\"organizationName\":\"创业智慧医院\",\"majorQualify\":null,\"appointmentType\":null,\"admitAddress\":null,\"regFee\":\"0.0\"}}";
    }


}
