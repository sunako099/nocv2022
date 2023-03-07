package com.suanko.graduationdesign.vo;

import com.suanko.graduationdesign.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends Role {
    private Integer page;
    private Integer limit;
}
