package com.suanko.graduationdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("approval_process")
public class Approval {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private String reason;
    private String address;
    private Integer day;
    private String phone;

    //和枚举类型对应
    private Integer nodeStatus;

    private Date createTime;
    private Date updateTime;

    @TableField(exist = false)
    private String username;
}
