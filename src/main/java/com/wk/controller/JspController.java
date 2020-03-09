package com.wk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.text.DateFormat;
import java.util.Date;

@ApiIgnore
@Controller
public class JspController {

    @RequestMapping("/getJsp")
    public String getJsp(Model model){
        model.addAttribute("now",DateFormat.getDateTimeInstance().format(new Date()));
        return "index";
    }

}
