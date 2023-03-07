package com.suanko.graduationdesign.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suanko.graduationdesign.entity.ChinaTotal;
import com.suanko.graduationdesign.entity.LineTrend;
import com.suanko.graduationdesign.entity.NocvData;
import com.suanko.graduationdesign.entity.NocvNews;
import com.suanko.graduationdesign.service.ChinaTotalService;
import com.suanko.graduationdesign.service.IndexService;
import com.suanko.graduationdesign.service.NocvNewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private ChinaTotalService chinaTotalService;
    @Autowired
    private NocvNewsService nocvNewsService;


    /**
     * 查询最新一条chinatotal数据
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) throws ParseException {
        //1.找到ID最大的一条数据
        Integer id = chinaTotalService.maxID();
        //2.根据ID进行查找数据
        ChinaTotal chinaTotal = chinaTotalService.getById(id);
        model.addAttribute("chinaTotal", chinaTotal);
        return "index";
    }

    @RequestMapping("/toChina")
    public String toChina(Model model) throws ParseException {
        //1.找到ID最大的一条数据
        Integer id = chinaTotalService.maxID();
        //2.根据ID进行查找数据


        //redis查询数据库逻辑
        /**
         * 1.查询redis缓存，有数据直接返回，无数据查询MySQL并更新缓存，返回客户端
         */
        Jedis jedis = new Jedis("127.0.0.1");

        //拿到客户端连接
        if (jedis != null) {
            String confirm = jedis.get("confirm");
            String input = jedis.get("input");
            String heal = jedis.get("heal");
            String dead = jedis.get("dead");
            String updateTime = jedis.get("updateTime");
            //缓存里有数据
            if (StringUtils.isNotBlank(confirm)
                    && StringUtils.isNotBlank(input)
                    && StringUtils.isNotBlank(heal)
                    && StringUtils.isNotBlank(dead)
                    && StringUtils.isNotBlank(updateTime)) {
                ChinaTotal chinaTotalRedis = new ChinaTotal();
                chinaTotalRedis.setConfirm(Integer.parseInt(confirm));
                chinaTotalRedis.setInput(Integer.parseInt(input));
                chinaTotalRedis.setHeal(Integer.parseInt(heal));
                chinaTotalRedis.setDead(Integer.parseInt(dead));
//                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-HH-dd HH:mm:ss");
//                Date date = simpleDateFormat.parse(updateTime);
                chinaTotalRedis.setUpdateTime(new Date());
                //扔回前台
                model.addAttribute("chinaTotal", chinaTotalRedis);
                //3.疫情播报新闻
                List<NocvNews> newsList = nocvNewsService.listNewsLimit5();
                model.addAttribute("newsList", newsList);
                return "china";
            } else {
                //缓存里无数据 查询数据
                ChinaTotal chinaTotal = chinaTotalService.getById(id);
                model.addAttribute("chinaTotal", chinaTotal);
                //3.疫情播报新闻
                List<NocvNews> newsList = nocvNewsService.listNewsLimit5();
                model.addAttribute("newsList", newsList);
                //更新缓存
                jedis.set("confirm", String.valueOf(chinaTotal.getConfirm()));
                jedis.set("input", String.valueOf(chinaTotal.getInput()));
                jedis.set("heal", String.valueOf(chinaTotal.getHeal()));
                jedis.set("dead", String.valueOf(chinaTotal.getDead()));
                jedis.set("updateTime", String.valueOf(chinaTotal.getUpdateTime()));


                return "china";
            }
        }
        ChinaTotal chinaTotal = chinaTotalService.getById(id);
        model.addAttribute("chinaTotal", chinaTotal);
        //3.疫情播报新闻
        List<NocvNews> newsList = nocvNewsService.listNewsLimit5();
        model.addAttribute("newsList", newsList);
        return "china";
    }


    @RequestMapping("/query")
    @ResponseBody
    public List<NocvData> queryData() throws ParseException {
        //每天更新一次的数据使用场景
//        QueryWrapper<NocvData> queryWrapper=new QueryWrapper<>();
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//        String format1=format.format(new Date());
//        queryWrapper.ge("update_time",format.parse(format1));
        //先查redis缓存【list】 有数据返回即可
        Jedis jedis = new Jedis("127.0.0.1");
        if (jedis != null) {
            //有缓存 返回即可
            List<String> listRedis = jedis.lrange("nocvdata", 0, 33);
            List<NocvData>dataList=new ArrayList<>();
            if (listRedis.size() > 0) {
                for (int i = 0; i < listRedis.size(); i++) {
                    String s = listRedis.get(i);
                    JSONObject jsonObject= JSONObject.parseObject(s);
                    Object name=jsonObject.get("name");
                    Object value=jsonObject.get("value");
                    NocvData nocvData=new NocvData();
                    nocvData.setName(String.valueOf(name));
                    nocvData.setValue(Integer.parseInt(value.toString()));
                    dataList.add(nocvData);
                }
                return dataList;
            } else {
                //redis无数据 查MySQL数据库，更新缓存
                List<NocvData> list=indexService.listOrderByIdLimit34();
                for (NocvData nocvData:list) {
                    jedis.lpush("nocvdata", JSONObject.toJSONString(nocvData));
                }
                return list;
            }
        }
        //兼容未安装redis
        List<NocvData> list = indexService.listOrderByIdLimit34();
        return list;
    }


    //跳转pie页面
    @RequestMapping("/toPie")
    public String toPie() {
        return "pie";
    }

    /**
     * 分组聚合
     */
    @RequestMapping("/queryPie")
    @ResponseBody
    public List<NocvData> queryPieData() {
        List<NocvData> list = indexService.listOrderByIdLimit34();
        return list;
    }

    //跳转bar页面
    @RequestMapping("/toBar")
    public String toBar() {
        return "bar";
    }

    @RequestMapping("/queryBar")
    @ResponseBody
    public Map<String, List<Object>> queryBarData() {
        //1.所有城市数据：数值
        List<NocvData> list = indexService.listOrderByIdLimit34();
        //2.所有城市数据
        List<String> cityList = new ArrayList<>();
        for (NocvData data : list) {
            cityList.add(data.getName());
        }
        //3.所有疫情数值数据
        List<Integer> dataList = new ArrayList<>();
        for (NocvData data : list) {
            dataList.add(data.getValue());
        }
        //4.创建map
        Map map = new HashMap<>();
        map.put("cityList", cityList);
        map.put("dataList", dataList);
        return map;
    }


    //跳转line页面
    @RequestMapping("/toLine")
    public String toLine() {
        return "line";
    }

    /**
     * SELECT * from data order by create_time limit 7;
     */
    @RequestMapping("/queryLine")
    @ResponseBody
    public Map<String, List<Object>> queryLineData() {
        //1.近七天查询所有数据
        List<LineTrend> list7Day = indexService.findSevenData();
        //2.封装所有确诊人数
        List<Integer> confirmList = new ArrayList<>();
        //3.封装所有隔离人数
        List<Integer> isolationList = new ArrayList<>();
        //4.封装所有治愈人数
        List<Integer> cureList = new ArrayList<>();
        //5.封装所有死亡人数
        List<Integer> deadList = new ArrayList<>();
        //6.封装所有疑似人数
        List<Integer> similarList = new ArrayList<>();
        //时间
        List<Date> dateList = new ArrayList<>();
        for (LineTrend data : list7Day) {
            confirmList.add(data.getConfirm());
            isolationList.add(data.getIsolation());
            cureList.add(data.getCure());
            deadList.add(data.getDead());
            similarList.add(data.getSimilar());
            dateList.add(data.getCreateTime());
        }
        //7.返回数据得格式容器Map
        Map map = new HashMap<>();
        map.put("confirmList", confirmList);
        map.put("isolationList", isolationList);
        map.put("cureList", cureList);
        map.put("deadList", deadList);
        map.put("similarList", similarList);
        map.put("dateList", dateList);

        return map;
    }


}
