package com.suanko.graduationdesign.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suanko.graduationdesign.dao.BanJiMapper;
import com.suanko.graduationdesign.dao.CommunitysMapper;
import com.suanko.graduationdesign.entity.BanJi;
import com.suanko.graduationdesign.entity.Communitys;
import com.suanko.graduationdesign.service.BanJiService;
import com.suanko.graduationdesign.service.CommunitysService;
import org.springframework.stereotype.Service;

@Service
public class CommunitysServiceImpl extends ServiceImpl<CommunitysMapper,Communitys> implements CommunitysService {
}
