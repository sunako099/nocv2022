package com.suanko.graduationdesign.vo;

import com.suanko.graduationdesign.entity.BanJi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BanJiVo extends BanJi {
    private Integer page;
    private Integer limit;
}
