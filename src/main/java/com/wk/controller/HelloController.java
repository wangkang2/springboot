package com.wk.controller;

import com.wk.conf.properties.Student;
import com.wk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ApiIgnore
@RestController
public class HelloController {

    @Autowired
    private Student student;

    @RequestMapping("/takenumber/dayprereg")
    public String hello(@RequestBody Map<String,Object> map){
        System.out.println(map);
        return "{  \"code\": \"200\",    \"message\": \"信息返回成功！\",     \"hyxh\": \"123\",     \"yyxh\": \"456\",    \"jzhm\": \"就诊号码\" , \"yysj\": \"预约日期\" }";
    }

    @RequestMapping("/takenumber/daypreregback")
    public String hello2(@RequestBody Map<String,Object> map){
        System.out.println(map);
        return "{  \"code\": \"200\",    \"message\": \"信息返回成功！\",     \"hyxh\": \"123\",     \"yyxh\": \"456\",    \"jzhm\": \"就诊号码\" , \"yysj\": \"预约日期\" }";
    }

    @GetMapping("/getUser")
    public User getUser(HttpServletRequest request){
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        User user = new User();
        user.setId(1);
        user.setName("123");
        user.setAge(18);
        return user;
    }

    public static void main(String[] args) {
        String url = "http://192.168.125.23:8888/report/cs.jpg";
        String newUrl = url.substring(url.indexOf("//")+2,url.indexOf("/",10));
        System.out.println(url.replace(newUrl,"125.74.218.136:8307"));
    }
}
