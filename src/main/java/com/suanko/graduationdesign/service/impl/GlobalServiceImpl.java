package com.suanko.graduationdesign.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suanko.graduationdesign.dao.GlobalDataMapper;
import com.suanko.graduationdesign.entity.NocvGlobalData;
import com.suanko.graduationdesign.service.GlobalService;
import org.springframework.stereotype.Service;

@Service
public class GlobalServiceImpl extends ServiceImpl<GlobalDataMapper, NocvGlobalData> implements GlobalService {
}
