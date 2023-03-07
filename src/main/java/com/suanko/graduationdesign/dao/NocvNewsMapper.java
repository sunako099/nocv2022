package com.suanko.graduationdesign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suanko.graduationdesign.entity.NocvNews;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NocvNewsMapper extends BaseMapper<NocvNews> {
    @Select("select * from nocv_news order by create_time desc limit 5 ;")
    List<NocvNews> listNewsLimit5();
}
