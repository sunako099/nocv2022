package com.suanko.graduationdesign.tengxunapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suanko.graduationdesign.entity.ChinaTotal;
import com.suanko.graduationdesign.entity.NocvData;
import com.suanko.graduationdesign.service.ChinaTotalService;
import com.suanko.graduationdesign.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ChinaTotalScheduleTask {

    @Autowired
    private ChinaTotalService chinaTotalService;

    @Autowired
    private IndexService indexService;

    /**
     * 每小时执行一次，更新全国数据
     * @throws IOException
     */
//    @Scheduled(fixedDelay=1000000)//1000s执行一次
    @Scheduled(cron="0 0 8,9,10,11,12,13,18,20 * * ?")//整点执行
    public void updateChinaTotalToDB() throws Exception {
        HttpUtils httpUtils=new HttpUtils();
        String string=httpUtils.getData();
        System.out.println("原始数据"+string);
        //1.所有数据的alibaba格式
        JSONObject jsonObject=JSONObject.parseObject(string);
        Object data=jsonObject.get("data");
        System.out.println("data:"+data);
        //2.Data
        JSONObject jsonObjectData=JSONObject.parseObject(data.toString());
        Object chinaTotal=jsonObjectData.get("chinaTotal");
        Object lastUpdateTime=jsonObjectData.get("overseaLastUpdateTime");
        System.out.println("chinaTotal:"+chinaTotal);
        //3.total 全中国整体疫情数据
        JSONObject jsonObjectTotal=JSONObject.parseObject(chinaTotal.toString());
        Object total=jsonObjectTotal.get("total");
        System.out.println("total:"+total);
        //4.全国数据 total
        JSONObject totalData=JSONObject.parseObject(total.toString());
        Object confirm=totalData.get("confirm");
        Object input=totalData.get("input");
        Object severe=totalData.get("severe");
        Object heal=totalData.get("heal");
        Object dead=totalData.get("dead");
        Object suspect=totalData.get("suspect");
        //5.为程序实体赋值
        ChinaTotal dataEntity=new ChinaTotal();
        dataEntity.setConfirm((Integer) confirm);
        dataEntity.setInput((Integer) input);
        dataEntity.setSevere((Integer) severe);
        dataEntity.setHeal((Integer) heal);
        dataEntity.setDead((Integer) dead);
        dataEntity.setSuspect((Integer) suspect);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataEntity.setUpdateTime(format.parse(String.valueOf(lastUpdateTime)));
        //6.插入数据库【插入】
        chinaTotalService.save(dataEntity);



        /**
         * 中国各个省份数据的自动更新
         */
        //拿到areaTree
        JSONArray areaTree=jsonObjectData.getJSONArray("areaTree");
        Object[] objects=areaTree.toArray();
        //遍历所有国家的数据
//        for (int i = 0; i < objects.length; i++) {
//            JSONObject jsonObject1= JSON.parseObject(objects[i].toString());
//            Object name=jsonObject1.get("name");
//            System.out.println(name);
//        }

        //拿到中国的数据
        JSONObject jsonObject1=JSONObject.parseObject(objects[2].toString());
        JSONArray children=jsonObject1.getJSONArray("children");//各个省份【JSON】
        Object[] objects1=children.toArray();//各个省份

        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<NocvData> list=new ArrayList<>();
        for (int i = 0; i < objects1.length; i++) {
            NocvData nocvData=new NocvData();
            JSONObject jsonObject2= JSONObject.parseObject(objects1[i].toString());
            Object name=jsonObject2.get("name");//省份名字
            Object timePro=jsonObject2.get("lastUpdateTime");//省份更新数据时间
            Object total1=jsonObject2.get("total");
            JSONObject jsonObject3=JSONObject.parseObject(total1.toString());//total
            Object confirm1=jsonObject3.get("confirm");//累计确诊数量

            //获取累计死亡人数和治愈人数
            Object heal1=jsonObject3.get("heal");
            Object dead1=jsonObject3.get("dead");
            //现存确诊
            int xianConfirm=Integer.parseInt(confirm1.toString())-Integer.parseInt(heal1.toString())-Integer.parseInt(dead1.toString());

//            System.out.println("省份-》"+name+":"+confirm1+"人");
            nocvData.setName(name.toString());
            nocvData.setValue(xianConfirm);
            if (timePro==null){
                nocvData.setUpdateTime(new Date());
            }else {
                nocvData.setUpdateTime(format1.parse(String.valueOf(timePro)));
            }
            list.add(nocvData);
        }

        //各个省份的数据插入数据库
        indexService.saveBatch(list);


        //7.删除缓存
        Jedis jedis=new Jedis("127.0.0.1");
        if (jedis!=null) {
            jedis.flushDB();
        }
    }
}
