package com.suanko.graduationdesign.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suanko.graduationdesign.dao.ChianTotalMapper;
import com.suanko.graduationdesign.entity.ChinaTotal;
import com.suanko.graduationdesign.service.ChinaTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChinaTotalServiceImpl extends ServiceImpl<ChianTotalMapper, ChinaTotal> implements ChinaTotalService {
    @Autowired
    private ChianTotalMapper chianTotalMapper;
    @Override
    public Integer maxID() {
        return chianTotalMapper.maxID();
    }
}
