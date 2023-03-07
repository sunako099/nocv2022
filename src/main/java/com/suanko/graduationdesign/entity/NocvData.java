package com.suanko.graduationdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("nocv_data")
public class NocvData {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer value;
    private Date updateTime;

}
