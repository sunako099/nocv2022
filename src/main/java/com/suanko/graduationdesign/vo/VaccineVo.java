package com.suanko.graduationdesign.vo;

import com.suanko.graduationdesign.entity.Vaccine;
import com.suanko.graduationdesign.entity.XueYuan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VaccineVo extends Vaccine {
    private Integer page;
    private Integer limit;
}
