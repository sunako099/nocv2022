package com.suanko.graduationdesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suanko.graduationdesign.entity.LineTrend;
import com.suanko.graduationdesign.entity.NocvData;

import java.util.List;

public interface IndexService extends IService<NocvData> {
    List<LineTrend> findSevenData();

    List<NocvData> listOrderByIdLimit34();
}
