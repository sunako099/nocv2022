package com.suanko.graduationdesign.vo;

import com.suanko.graduationdesign.entity.Communitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommunitysVo extends Communitys {
    private Integer page;
    private Integer limit;
}
