package com.suanko.graduationdesign.vo;

import com.suanko.graduationdesign.entity.NocvData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NocvDataVo extends NocvData {
    private Integer page;
    private Integer limit;
}
