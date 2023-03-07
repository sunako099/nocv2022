package com.suanko.graduationdesign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suanko.graduationdesign.entity.ChinaTotal;
import org.apache.ibatis.annotations.Select;

public interface ChianTotalMapper extends BaseMapper<ChinaTotal> {
    @Select("select max(id) from china_total")
    Integer maxID();
}
