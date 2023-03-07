package com.suanko.graduationdesign.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suanko.graduationdesign.api.CovidRiskZoneApi;
import com.suanko.graduationdesign.entity.Communitys;
import com.suanko.graduationdesign.service.CommunitysService;
import com.suanko.graduationdesign.vo.CommunitysVo;
import com.suanko.graduationdesign.vo.DataView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CovidRiskZoneController {

    @Autowired
    private CommunitysService communitysService;

    @RequestMapping("/zone/toZone")
    public String toZone(){
        return "zone/zone";
    }

    @RequestMapping("/zone/searchRiskZone")
    @ResponseBody
    public DataView searchCovidRiskZoneData(CommunitysVo communitysVo,String province, String city, String county) throws IOException {
        //必填项
        if (StringUtils.isEmpty(province)){
            province="北京市";
        }

        //1.解析接口数据
        String riskZoneData = CovidRiskZoneApi.getCovidRiskZoneData(province,city,county);
        //2.拿到社区的数据封装List集合
        List<Communitys> list=new ArrayList<>();
        //fastjson解析实体
        if (StringUtils.isNotEmpty(riskZoneData)){
            JSONObject jsonObject = JSONObject.parseObject(riskZoneData);
            JSONObject data = jsonObject.getJSONObject("data");
            Integer high_count=data.getInteger("high_count");
            String end_update_time=(String)data.get("end_update_time");
            //解析
            JSONArray high_list=data.getJSONArray("high_list");
            for(int i=0;i<high_list.size();i++){
                JSONObject high_listJSONObject = high_list.getJSONObject(i);
                String area_name=(String) high_list.getJSONObject(i).get("area_name");
                JSONArray communitys = high_listJSONObject.getJSONArray("communitys");
                for (int j=0; j<communitys.size();j++){
                    String s=communitys.get(j).toString();
                    Communitys community=new Communitys();
                    community.setCommunity(s);
                    community.setTime(end_update_time);
                    community.setCount(high_count);
                    community.setArea_name(area_name);
                    list.add(community);
                }
            }

//            //3.做好分页配置
//            IPage<Communitys> iPage=new Page<>(communitysVo.getPage(),communitysVo.getLimit());
//            communitysService.page(iPage,queryWrapper);
            return new DataView(list);
        }
        return null;
    }
}
