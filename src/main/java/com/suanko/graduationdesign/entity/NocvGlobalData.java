package com.suanko.graduationdesign.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("nocv_global_data")
public class NocvGlobalData {
    //    private Integer id;
    private String name;
    private Integer value;
}
