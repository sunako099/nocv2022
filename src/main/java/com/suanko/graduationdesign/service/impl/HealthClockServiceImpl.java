package com.suanko.graduationdesign.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suanko.graduationdesign.dao.HealthClockMapper;
import com.suanko.graduationdesign.entity.HealthClock;
import com.suanko.graduationdesign.service.HealthClockService;
import org.springframework.stereotype.Service;

@Service
public class HealthClockServiceImpl extends ServiceImpl<HealthClockMapper, HealthClock> implements HealthClockService {
}
