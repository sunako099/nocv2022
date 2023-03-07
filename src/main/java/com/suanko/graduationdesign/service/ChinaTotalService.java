package com.suanko.graduationdesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suanko.graduationdesign.entity.ChinaTotal;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

public interface ChinaTotalService extends IService<ChinaTotal> {

    Integer maxID();
}
