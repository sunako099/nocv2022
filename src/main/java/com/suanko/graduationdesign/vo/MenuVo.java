package com.suanko.graduationdesign.vo;

import com.suanko.graduationdesign.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MenuVo extends Menu {
    private Integer page;
    private Integer limit;
}
