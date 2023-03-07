package com.suanko.graduationdesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suanko.graduationdesign.entity.Approval;
import com.suanko.graduationdesign.entity.HeSuan;
import com.suanko.graduationdesign.entity.Role;
import com.suanko.graduationdesign.entity.User;
import com.suanko.graduationdesign.enums.ApprovalNodeStatusEnum;
import com.suanko.graduationdesign.service.ApprovalService;
import com.suanko.graduationdesign.service.RoleService;
import com.suanko.graduationdesign.vo.ApprovalVo;
import com.suanko.graduationdesign.vo.DataView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/approval")
public class ApprovalController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ApprovalService approvalService;


    @RequestMapping("/toApproval")
    public String toHeSuan(){
        return "approval/approval";
    }

    /**
     * 加载自己的审批
     */
    @RequestMapping("/loadMyApproval")
    @ResponseBody
    public DataView loadMyApproval(ApprovalVo approvalVo, HttpSession session){
        //1.取到
        User user=(User)session.getAttribute("user");
        if (StringUtils.isNotEmpty(user.getUsername())){
            String username=user.getUsername();
            Integer uid = user.getId();
            IPage<Approval> page=new Page<>(approvalVo.getPage(),approvalVo.getLimit());
            QueryWrapper<Approval> queryWrapper=new QueryWrapper<>();
            queryWrapper.ge(StringUtils.isNotBlank(String.valueOf(uid)), "uid", uid);
            approvalService.page(page,queryWrapper);
            //处理名字
            List<Approval> records=page.getRecords();
            for (Approval a: records) {
                a.setUsername(username);
            }
            return new DataView(page.getTotal(),records);
        }
        return new DataView();
    }

    @RequestMapping("/addApproval")
    @ResponseBody
    public DataView addApproval(Approval approval,HttpSession session){
        approval.setCreateTime(new Date());
        //失去未提交状态，直接提交
        User user=(User) session.getAttribute("user");
        Integer id= user.getId();
        approval.setUid(id);
        //多种角色，最好每个人一个角色
        List<Integer> role=roleService.queryUserRoleById(id);
        Integer integer=role.get(0);
        Role byId=roleService.getById(integer);
        String roleName= byId.getName();
        if (StringUtils.equals(roleName,"admin") || StringUtils.equals(roleName,"学生")){
            approval.setNodeStatus(ApprovalNodeStatusEnum.TEACHER_ING.getCode());
        }else if (StringUtils.equals(roleName,"教师")){
            approval.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_ING.getCode());
        }else if (StringUtils.equals(roleName,"院系")){
            approval.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_PASSED.getCode());
        }else {
            approval.setNodeStatus(ApprovalNodeStatusEnum.TEACHER_ING.getCode());
        }
        approvalService.save(approval);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("申请请假成功！");
        return dataView;
    }




    @RequestMapping("/successApproval")
    @ResponseBody
    public DataView successApproval(Approval approval,HttpSession session){
        DataView dataView=new DataView();
        approval.setUpdateTime(new Date());
        //1.首先判断是不是数据库中 审核中的状态
        User user=(User) session.getAttribute("user");
        Integer id= user.getId();
        String username = user.getUsername();
        approval.setUid(id);
        //多种角色，最好每个人一个角色
        List<Integer> role=roleService.queryUserRoleById(id);
        Integer integer=role.get(0);
        Role byId=roleService.getById(integer);
        String roleName= byId.getName();
        if (StringUtils.equals(roleName,"学生")){
            dataView.setCode(100);
            dataView.setMsg(roleName+"角色不可进行审批！");
            return dataView;
        }
        //2.查询节点状态
        Approval serviceById=approvalService.getById(approval.getId());
        Integer nodeStatus=serviceById.getNodeStatus();
        //判断是否为审批中的状态
        if ((StringUtils.equals(String.valueOf(ApprovalNodeStatusEnum.TEACHER_ING.getCode()),String.valueOf(nodeStatus)))
           || (StringUtils.equals(String.valueOf(ApprovalNodeStatusEnum.COLLEGE_ING.getCode()),String.valueOf(nodeStatus)))
        ){
            if (StringUtils.equals(roleName,"admin")){
                approval.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_PASSED.getCode());
            } else if (StringUtils.equals(roleName,"教师")){
                approval.setNodeStatus(ApprovalNodeStatusEnum.TEACHER_PASSED.getCode());
            }else if (StringUtils.equals(roleName,"院系")){
                approval.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_PASSED.getCode());
            }else {
                approval.setNodeStatus(ApprovalNodeStatusEnum.TEACHER_PASSED.getCode());
            }
            //修改库
            approvalService.updateById(approval);
            dataView.setCode(200);
            dataView.setMsg(username+":角色："+roleName+"审批成功！");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("此状态不可审批！");
        return dataView;
    }


    @RequestMapping("/rejectApproval")
    @ResponseBody
    public DataView rejectApproval(Approval approval,HttpSession session){
        DataView dataView=new DataView();
        approval.setUpdateTime(new Date());
        //1.首先判断是不是数据库中 审核中的状态
        User user=(User) session.getAttribute("user");
        Integer id= user.getId();
        String username = user.getUsername();
        approval.setUid(id);
        //多种角色，最好每个人一个角色
        List<Integer> role=roleService.queryUserRoleById(id);
        Integer integer=role.get(0);
        Role byId=roleService.getById(integer);
        String roleName= byId.getName();
        if (StringUtils.equals(roleName,"学生")){
            dataView.setCode(100);
            dataView.setMsg(roleName+"角色不可进行审批！");
            return dataView;
        }
        //2.查询节点状态
        Approval serviceById=approvalService.getById(approval.getId());
        Integer nodeStatus=serviceById.getNodeStatus();
        //判断是否为审批中的状态
        if ((StringUtils.equals(String.valueOf(ApprovalNodeStatusEnum.TEACHER_ING.getCode()),String.valueOf(nodeStatus)))
                || (StringUtils.equals(String.valueOf(ApprovalNodeStatusEnum.COLLEGE_ING.getCode()),String.valueOf(nodeStatus)))
        ){
            if (StringUtils.equals(roleName,"admin")){
                approval.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_REJECTED.getCode());
            } else if (StringUtils.equals(roleName,"教师")){
                approval.setNodeStatus(ApprovalNodeStatusEnum.TEACHER_REJECTED.getCode());
            }else if (StringUtils.equals(roleName,"院系")){
                approval.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_REJECTED.getCode());
            }else {
                approval.setNodeStatus(ApprovalNodeStatusEnum.TEACHER_REJECTED.getCode());
            }
            //修改库
            approvalService.updateById(approval);
            dataView.setCode(200);
            dataView.setMsg(username+":角色："+roleName+"驳回成功！");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("此状态不可驳回！");
        return dataView;
    }
}
