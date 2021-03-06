package com.wk.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FeeController {

    @PostMapping("/listOutpatientExpenses")
    public String listOutpatientExpenses(@RequestBody Map map){
        System.out.println(map);
        return "{\n" +
                "\t\"package\": {\n" +
                "\t\t\"head\": {\n" +
                "\t\t\t\"busseID\": \"1800\",\n" +
                "\t\t\t\"sendTradeNum\": \"20150701083030-10011001-0001\",\n" +
                "\t\t\t\"senderCode\": \"001\",\n" +
                "\t\t\t\"senderName\": \"消息发送者名称\",\n" +
                "\t\t\t\"receiverCode\": \"002\",\n" +
                "\t\t\t\"receiverName\": \"消息接受者的名称\",\n" +
                "\t\t\t\"intermediaryCode\": \"003\",\n" +
                "\t\t\t\"intermediaryName\": \"第三方的名称\",\n" +
                "\t\t\t\"hosorgNum\": \"001\",\n" +
                "\t\t\t\"hosorgName\": \"操作员姓名\",\n" +
                "\t\t\t\"systemType\": \"1\",\n" +
                "\t\t\t\"busenissType\": \"8\",\n" +
                "\t\t\t\"standardVersionCode\": \"version:1.0.0\",\n" +
                "\t\t\t\"clientmacAddress \": \"30BB7E0A5E2D \",\n" +
                "\t\t\t\" recordCount \": \"1\"\n" +
                "\t\t},\n" +
                "\t\t\"body\": [\n" +
                "\t\t\t{}\n" +
                "\t\t],\n" +
                "\t\t\"additionInfo\": {\n" +
                "\t\t\t\"errorCode\": \"0\",\n" +
                "\t\t\t\"errorMsg\": \"\",\n" +
                "\t\t\t\"receiverTradeNum\": \"20150701083030-10012231-0001\",\n" +
                "\t\t\t\"correlationId\": \"\",\n" +
                "\t\t\t\"asyncAsk\": \"0\",\n" +
                "\t\t\t\"callback\": \"\",\n" +
                "\t\t\t\"curDllAddr\": \"\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
    }

    @PostMapping("/outpatientExpensesDetail")
    public String outpatientExpensesDetail(@RequestBody Map map){
        System.out.println(map);
        return "{\"code\":\"1\",\"message\":\"成功\",\"data\":{\"hospitalCode\":\"620602001\",\"hospitalName\":\"凉州医院中心卫生院\",\"doctorName\":\"就诊医生姓名\",\"departmentName\":\"就诊科室名称\",\"costDate\":\"2019-09-20 10:00:00\",\"medicalDate\":\"2019-09-20 10:00:00\",\"patientName\":\"病人姓名\",\"patientCode\":\"111\",\"patientMedicalCardTypeText\":\"诊疗卡类型\",\"patientMedicalCardNumber\":\"诊疗卡号\",\"patientNature\":\"病人性质\",\"medicalInsuranceText\":\"医保类型名称\",\"invoiceNumber\":\"发票号码\",\"invoiceStatus\":\"已打印\",\"costAmount\":\"100.00\",\"selfPayAmount\":\"100.00\",\"reimburseAmount\":\"0.00\",\"itemDetails\":[{\"itemType\":\"2\",\"itemName\":\"中成药\",\"itemAmount\":\"100.00\",\"introduce\":\"项目介绍\",\"itemRemark\":\"项目备注\",\"itemList\":[{\"itemName\":\"金银花\",\"itemQuantity\":\"1\",\"itemUnit\":\"kg\",\"itemUnitPrice\":\"100.00\",\"specifications\":\"规格\",\"usage\":\"用法\",\"dosage\":\"用量\",\"frequency\":\"频次\"}]}],\"settlementDetails\":[{\"feeName\":\"微信支付\",\"feeValue\":\"100.00\",\"feeRemark\":\"支付说明\"}]}}";
    }

    @PostMapping("/listBill")
    public String listBill(@RequestBody Map map){
        System.out.println(map);
        return "{\"code\":\"1\",\"message\":\"成功\",\"data\":{\"costAmount\":\"200.00\",\"selfPayAmount\":\"200.00\",\"reimburseAmount\":\"0.00\",\"costList\":[{\"hospitalCode\":\"620602001\",\"hospitalName\":\"凉州医院中心卫生院\",\"amount\":\"200.00\",\"vdType\":\"门诊\",\"costDate\":\"2019-08-23\",\"invoiceNumber\":\"111\",\"invoiceStatus\":\"已打印\",\"patientCode\":\"111\",\"remark\":\"\"}]}}";
    }

    @PostMapping("/listHospitalizationPayment")
    public String listHospitalizationPayment(@RequestBody Map map){
        System.out.println(map);
        return "{\"code\":\"1\",\"message\":\"成功\",\"data\":{\"hospitalCode\":\"620602001\",\"hospitalName\":\"凉州医院中心卫生院\",\"doctorName\":\"就诊医生姓名\",\"departmentName\":\"就诊科室名称\",\"costDate\":\"2019-09-20 10:00:00\",\"medicalDate\":\"2019-09-20 10:00:00\",\"patientName\":\"病人姓名\",\"patientCode\":\"111\",\"patientMedicalCardTypeText\":\"诊疗卡类型\",\"patientMedicalCardNumber\":\"诊疗卡号\",\"patientNature\":\"病人性质\",\"medicalInsuranceText\":\"医保类型名称\",\"invoiceNumber\":\"发票号码\",\"invoiceStatus\":\"已打印\",\"costAmount\":\"100.00\",\"selfPayAmount\":\"100.00\",\"reimburseAmount\":\"0.00\",\"itemDetails\":[{\"itemType\":\"2\",\"itemName\":\"中成药\",\"itemAmount\":\"100.00\",\"introduce\":\"项目介绍\",\"itemRemark\":\"项目备注\",\"itemList\":[{\"itemName\":\"金银花\",\"itemQuantity\":\"1\",\"itemUnit\":\"kg\",\"itemUnitPrice\":\"100.00\",\"specifications\":\"规格\",\"usage\":\"用法\",\"dosage\":\"用量\",\"frequency\":\"频次\"}]}],\"settlementDetails\":[{\"feeName\":\"微信支付\",\"feeValue\":\"100.00\",\"feeRemark\":\"支付说明\"}]}}";
    }


    @PostMapping("/hospitalization/listHospitalizationRecord")
    public String listHospitalizationRecord(@RequestBody Map map){
        System.out.println(map);
        return "{\"code\":\"1\",\"message\":\"成功\",\"data\":[{\"inHospitalRecordNumber\":\"住院号\",\"inHospitalRecordCode\":\"住院号码\",\"departmentName\":\"住院科室\",\"bedNumber\":\"病床号\",\"status\":2,\"inDate\":\"2019-08-01\",\"outDate\":\"2019-08-07\",\"totalFee\":100.00,\"balance\":30.00,\"prepaidGold\":30.00,\"otherDescription\": \"其他说明\"}]}";
    }

    @PostMapping("/onedaybill/listBill")
    public String onedaybill(@RequestBody Map map){
        System.out.println(map);
        if("2019-09-19".equals(map.get("costDate")))
            return "{\"code\":\"1\",\"message\":\"成功\",\"data\":[{\"totalCost\":53.21,\"patientName\":\"姓名\",\"inHospitalRecordNumber\":\"住院号\",\"inHospitalRecordCode\":\"住院号码\",\"departmentName\":\"住院科室\",\"bedNumber\":\"病床号\",\"inDate\":\"2019-08-01 12:00:00\",\"outDate\":\"2019-08-07 18:00:00\",\"totalFee\":53.19,\"balance\":53.20,\"credit\":53.00,\"mainCostList\":[{\"chargeName\":\"费用大类名称\",\"amount\":50.00,\"costList\":[{\"costName\":\"费用名称\",\"costUnit\":\"单位\",\"costQuantity\":15.31,\"costUnitPrice\":15.31,\"costSubtotal\":15.31}]}]}]}";
        else
            return "{\"code\":\"0\",\"message\":\"无支付信息\",\"data\":[]}";
    }

}
