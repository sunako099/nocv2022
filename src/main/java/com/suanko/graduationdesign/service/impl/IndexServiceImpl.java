package com.suanko.graduationdesign.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suanko.graduationdesign.dao.IndexMapper;
import com.suanko.graduationdesign.entity.LineTrend;
import com.suanko.graduationdesign.entity.NocvData;
import com.suanko.graduationdesign.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl extends ServiceImpl<IndexMapper, NocvData> implements IndexService {
    @Autowired
    private IndexMapper indexMapper;
    @Override
    public List<LineTrend> findSevenData() {
        List<LineTrend> list=indexMapper.findSevenData();
        return list;
    }

    @Override
    public List<NocvData> listOrderByIdLimit34() {
        return indexMapper.listOrderByIdLimit34();
    }
}
