package com.suanko.graduationdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role_menu")
public class RoleMenu {
    private Integer rid;
    private Integer mid;

}
