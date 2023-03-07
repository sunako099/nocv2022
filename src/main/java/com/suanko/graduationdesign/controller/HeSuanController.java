package com.suanko.graduationdesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suanko.graduationdesign.entity.HeSuan;
import com.suanko.graduationdesign.service.HeSuanService;
import com.suanko.graduationdesign.vo.DataView;
import com.suanko.graduationdesign.vo.HeSuanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hesuan")
public class HeSuanController {

    @Autowired
    private HeSuanService heSuanService;

    @RequestMapping("/toHeSuan")
    public String toHeSuan(){
        return "hesuan/hesuan";
    }

    @RequestMapping("/loadAllHeSuan")
    @ResponseBody
    public DataView loadAllHeSuan(HeSuanVo heSuanVo){
        IPage<HeSuan> page=new Page<>(heSuanVo.getPage(),heSuanVo.getLimit());
        QueryWrapper<HeSuan> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(!(heSuanVo.getName() == null),"name",heSuanVo.getName());
        heSuanService.page(page,queryWrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }

    @RequestMapping("/addHeSuan")
    @ResponseBody
    public DataView addHeSuan(HeSuan heSuan){
        heSuanService.save(heSuan);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("添加核酸检测成功！");
        return dataView;
    }

    @RequestMapping("/updateHeSuan")
    @ResponseBody
    public DataView updateHeSuan(HeSuan heSuan){
        heSuanService.updateById(heSuan);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("修改核酸检测成功！");
        return dataView;
    }

    @RequestMapping("/deleteHeSuan")
    @ResponseBody
    public DataView deleteHeSuan(Integer id){
        heSuanService.removeById(id);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除核酸检测成功！");
        return dataView;
    }
}
