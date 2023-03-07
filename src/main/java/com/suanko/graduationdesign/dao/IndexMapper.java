package com.suanko.graduationdesign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suanko.graduationdesign.entity.LineTrend;
import com.suanko.graduationdesign.entity.NocvData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface IndexMapper extends BaseMapper<NocvData> {
    @Select("select * from line_trend order by create_time desc limit 7")
    List<LineTrend> findSevenData();

    @Select("select * from nocv_data order by id desc limit 34")
    List<NocvData> listOrderByIdLimit34();
}
