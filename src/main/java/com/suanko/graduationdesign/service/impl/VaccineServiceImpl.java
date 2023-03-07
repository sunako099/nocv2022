package com.suanko.graduationdesign.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suanko.graduationdesign.dao.BanJiMapper;
import com.suanko.graduationdesign.dao.VaccineMapper;
import com.suanko.graduationdesign.entity.BanJi;
import com.suanko.graduationdesign.entity.Vaccine;
import com.suanko.graduationdesign.service.BanJiService;
import com.suanko.graduationdesign.service.VaccineService;
import org.springframework.stereotype.Service;

@Service
public class VaccineServiceImpl extends ServiceImpl<VaccineMapper, Vaccine> implements VaccineService {
}
