package com.suanko.graduationdesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suanko.graduationdesign.entity.BanJi;
import com.suanko.graduationdesign.entity.HeSuan;
import com.suanko.graduationdesign.entity.Vaccine;
import com.suanko.graduationdesign.service.VaccineService;
import com.suanko.graduationdesign.vo.BanJiVo;
import com.suanko.graduationdesign.vo.DataView;
import com.suanko.graduationdesign.vo.HeSuanVo;
import com.suanko.graduationdesign.vo.VaccineVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/vaccine")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @RequestMapping("/toVaccine")
    public String toVaccine(){
        return "vaccine/vaccine";
    }

    @RequestMapping("/loadAllVaccine")
    @ResponseBody
    public DataView loadAllVaccine(VaccineVo vaccineVo){
        IPage<Vaccine> page=new Page<>(vaccineVo.getPage(),vaccineVo.getLimit());
        QueryWrapper<Vaccine> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(!(vaccineVo.getName() == null),"name",vaccineVo.getName());
        vaccineService.page(page,queryWrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }

    @RequestMapping("/addVaccine")
    @ResponseBody
    public DataView addVaccine(Vaccine vaccine){
        vaccineService.save(vaccine);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("添加疫苗成功！");
        return dataView;
    }

    @RequestMapping("/updateVaccine")
    @ResponseBody
    public DataView updateVaccine(Vaccine vaccine){
        vaccineService.updateById(vaccine);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("修改疫苗成功！");
        return dataView;
    }

    @RequestMapping("/deleteVaccine")
    @ResponseBody
    public DataView deleteVaccine(Integer id){
        vaccineService.removeById(id);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除疫苗成功！");
        return dataView;
    }
}
