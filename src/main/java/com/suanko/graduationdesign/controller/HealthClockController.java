package com.suanko.graduationdesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suanko.graduationdesign.entity.HealthClock;
import com.suanko.graduationdesign.service.HealthClockService;
import com.suanko.graduationdesign.vo.DataView;
import com.suanko.graduationdesign.vo.HealthClockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthClockController {

    @Autowired
    private HealthClockService healthClockService;



    @RequestMapping("/toHealthClock")
    public String toHealthClock(){
        return "admin/healthclock";
    }


    /**
     * 查询所有打卡记录
     * @param healthClockVo
     * @return
     */
    @RequestMapping("/listHealthClock")
    @ResponseBody
    public DataView listHealthClock(HealthClockVo healthClockVo){
        //查询所有带有模糊查询条件   带有分页
        IPage<HealthClock> page=new Page<>(healthClockVo.getPage(),healthClockVo.getLimit());
        QueryWrapper<HealthClock> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(!(healthClockVo.getUsername() == null),"username",healthClockVo.getUsername());
        queryWrapper.like(!(healthClockVo.getPhone() == null),"phone", healthClockVo.getPhone());
        healthClockService.page(page,queryWrapper);
        DataView dataView=new DataView(page.getTotal(),page.getRecords());
        return dataView;
    }


    /**
     * 添加或修改健康打卡记录
     */
    @RequestMapping("/addHealthClock")
    @ResponseBody
    public DataView addHealthClock(HealthClock healthClock){
        boolean b=healthClockService.saveOrUpdate(healthClock);
        DataView dataView=new DataView();
        if (b){
            dataView.setCode(200);
            dataView.setMsg("更新成功！");
            return dataView;
        }
            dataView.setCode(100);
            dataView.setMsg("更新失败！");
            return dataView;

    }

    /**
     * 删除健康打卡记录
     */
    @RequestMapping("/deleteHealthClockById")
    @ResponseBody
    public DataView deleteHealthClockById(Integer id){
        healthClockService.removeById(id);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除数据成功！");
        return dataView;
    }
}
