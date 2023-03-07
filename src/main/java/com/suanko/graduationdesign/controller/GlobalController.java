package com.suanko.graduationdesign.controller;

import com.suanko.graduationdesign.entity.NocvData;
import com.suanko.graduationdesign.entity.NocvGlobalData;
import com.suanko.graduationdesign.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GlobalController {
    @Autowired
    private GlobalService globalService;
    @RequestMapping("/toGlobal")
    public String toGlobal(){
        return "global";
    }

    @RequestMapping("/queryGlobal")
    @ResponseBody
    public List<NocvGlobalData> queryGlobal(){
        List<NocvGlobalData> list=globalService.list();
        return list;
    }

    /**
     * 全国疫情新增趋势图
     */
    @RequestMapping("/trend/toIncrease")
    public String toTrendIncrease(){
        return "trendIncrease";
    }
}
