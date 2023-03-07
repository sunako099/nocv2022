package com.suanko.graduationdesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suanko.graduationdesign.entity.BanJi;
import com.suanko.graduationdesign.service.BanJiService;
import com.suanko.graduationdesign.vo.BanJiVo;
import com.suanko.graduationdesign.vo.DataView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/banji")
public class BanJiController {
    @Autowired
    private BanJiService banJiService;



    @GetMapping("/banji/listBanJi")
    public DataView listNews(BanJiVo banJiVo){
        QueryWrapper<BanJi> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(banJiVo.getName()),"name",banJiVo.getName());
        IPage<BanJi> iPage=new Page<>(banJiVo.getPage(),banJiVo.getLimit());
        banJiService.page(iPage,queryWrapper);
        return new DataView(iPage.getTotal(),iPage.getRecords());
    }

    @DeleteMapping("/banji/deleteById")
    public DataView deleteById(Integer id){
        banJiService.removeById(id);
        DataView dataView=new DataView();
        dataView.setMsg("删除成功！");
        dataView.setCode(200);
        return dataView;
    }

    @PostMapping("/banji/addOrUpdateBanJi")
    public DataView addOrUpdate(BanJi banJi){
        banJiService.saveOrUpdate(banJi);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("新增或修改成功！");
        return dataView;
    }
}
