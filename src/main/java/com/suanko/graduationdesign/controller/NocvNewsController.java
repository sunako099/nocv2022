package com.suanko.graduationdesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suanko.graduationdesign.entity.NocvNews;
import com.suanko.graduationdesign.service.NocvNewsService;
import com.suanko.graduationdesign.vo.DataView;
import com.suanko.graduationdesign.vo.NocvNewsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/news")
public class NocvNewsController {
    @Autowired
    private NocvNewsService nocvNewsService;

    @RequestMapping("/toNews")
    public String toNews(){
        return "news/news";
    }

    @RequestMapping("/listNews")
    @ResponseBody
    public DataView listNews(NocvNewsVo nocvNewsVo){
        QueryWrapper<NocvNews> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(nocvNewsVo.getTitle()),"title",nocvNewsVo.getTitle());
        IPage<NocvNews>iPage=new Page<>(nocvNewsVo.getPage(),nocvNewsVo.getLimit());
        nocvNewsService.page(iPage,queryWrapper);
        return new DataView(iPage.getTotal(),iPage.getRecords());
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        nocvNewsService.removeById(id);
        DataView dataView=new DataView();
        dataView.setMsg("删除成功！");
        dataView.setCode(200);
        return dataView;
    }

    @RequestMapping("/addOrUpdateNews")
    @ResponseBody
    public DataView addOrUpdate(NocvNews nocvNews){
        nocvNews.setCreateTime(new Date());
        nocvNewsService.saveOrUpdate(nocvNews);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("新增或修改成功！");
        return dataView;
    }
}
