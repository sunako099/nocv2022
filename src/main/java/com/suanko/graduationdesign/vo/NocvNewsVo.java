package com.suanko.graduationdesign.vo;


import com.suanko.graduationdesign.entity.NocvNews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NocvNewsVo extends NocvNews {
    private Integer page;
    private Integer limit;
}
