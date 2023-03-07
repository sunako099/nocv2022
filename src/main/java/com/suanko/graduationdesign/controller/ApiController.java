package com.suanko.graduationdesign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 专门跳转页面
 */
@Controller
public class ApiController {
    //跳转页面
    @RequestMapping("toChinaManager")
    public String toChinaData(){
        return "admin/chinadatamanager";
    }

    @RequestMapping("/banji/toBanJi")
    public String toNews(){
        return "banji/banji";
    }
}
