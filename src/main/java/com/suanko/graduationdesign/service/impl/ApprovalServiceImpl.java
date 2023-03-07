package com.suanko.graduationdesign.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suanko.graduationdesign.dao.ApprovalMapper;
import com.suanko.graduationdesign.entity.Approval;
import com.suanko.graduationdesign.service.ApprovalService;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl extends ServiceImpl<ApprovalMapper, Approval> implements ApprovalService {
}
