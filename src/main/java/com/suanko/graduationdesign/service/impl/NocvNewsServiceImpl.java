package com.suanko.graduationdesign.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suanko.graduationdesign.dao.NocvNewsMapper;
import com.suanko.graduationdesign.entity.NocvNews;
import com.suanko.graduationdesign.service.NocvNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NocvNewsServiceImpl extends ServiceImpl<NocvNewsMapper, NocvNews> implements NocvNewsService {

    @Autowired
    private NocvNewsMapper nocvNewsMapper;

    @Override
    public List<NocvNews> listNewsLimit5() {
        return nocvNewsMapper.listNewsLimit5();
    }
}
