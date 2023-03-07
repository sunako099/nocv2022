package com.suanko.graduationdesign.vo;

import com.suanko.graduationdesign.entity.Approval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApprovalVo extends Approval {
    private Integer page;
    private Integer limit;
}
