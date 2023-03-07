package com.suanko.graduationdesign.vo;

import com.suanko.graduationdesign.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User {
    private Integer page;
    private Integer limit;
}
