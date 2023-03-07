package com.suanko.graduationdesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suanko.graduationdesign.entity.BanJi;
import com.suanko.graduationdesign.entity.Role;
import com.suanko.graduationdesign.entity.User;
import com.suanko.graduationdesign.entity.XueYuan;
import com.suanko.graduationdesign.service.BanJiService;
import com.suanko.graduationdesign.service.RoleService;
import com.suanko.graduationdesign.service.UserService;
import com.suanko.graduationdesign.service.XueYuanService;
import com.suanko.graduationdesign.vo.DataView;
import com.suanko.graduationdesign.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BanJiService banJiService;
    @Autowired
    private XueYuanService xueYuanService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/toUser")
    public String toUser(){
        return "user/user";
    }

    @RequestMapping("/toChangePassword")
    public String toChangePassword(){
        return "user/changepassword";
    }

    @RequestMapping("/toUserInfo")
    public String toUserInfo(){
        return "user/userInfo";
    }

    /**
     * 分页查询用户数据 带查询条件
     * @param userVo
     * @return
     */
    @RequestMapping("/loadAllUser")
    @ResponseBody
    public DataView getAllUser(UserVo userVo){
        QueryWrapper<User> queryWrapper= new QueryWrapper<>();
        IPage<User> page=new Page<>(userVo.getPage(), userVo.getLimit());
        queryWrapper.like(StringUtils.isNotBlank(userVo.getUsername()),"username",userVo.getUsername());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getPhone()),"phone",userVo.getPhone());
        IPage<User> iPage=userService.page(page,queryWrapper);
        //为班级名字进行赋值
        for (User user: iPage.getRecords()) {
            //为班级名字赋值
            if (user.getBanJiId()!=null){
                //班级banjiService查库
                BanJi banji=banJiService.getById(user.getBanJiId());
                user.setBanJiName(banji.getName());
            }
            //为学院名字赋值
            if (user.getXueYuanId()!=null){
                XueYuan xueYuan=xueYuanService.getById(user.getXueYuanId());
                user.setXueYuanName(xueYuan.getName());
            }
            //为老师名字赋值
            if (user.getTeacherId()!=null){
                User teacher=userService.getById(user.getTeacherId());
                user.setTeacherName(teacher.getUsername());
            }
        }
        return new DataView(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * 新增用户
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public DataView addUser(User user){
        userService.save(user);
        DataView dataView=new DataView();
        dataView.setMsg("添加用户成功！");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 修改用户
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public DataView updateUser(User user){
        userService.updateById(user);
        DataView dataView=new DataView();
        dataView.setMsg("修改用户成功！");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 删除用户
     */
    @RequestMapping("/deleteUser/{id}")
    @ResponseBody
    public DataView deleteUser(@PathVariable("id")Integer id){
        userService.removeById(id);
        DataView dataView=new DataView();
        dataView.setMsg("删除用户成功！");
        dataView.setCode(200);
        return dataView;
    }



    /**
     * 初始化下拉列表的数据【班级】
     */
    @RequestMapping("/listAllBanJi")
    @ResponseBody
    public List<BanJi> listAllBanJi(){
        List<BanJi> list=banJiService.list();
        return list;
    }


    /**
     * 初始化下拉列表的数据【学院】
     */
    @RequestMapping("/listAllXueYuan")
    @ResponseBody
    public List<XueYuan> listAllXueYuan(){
        List<XueYuan> list=xueYuanService.list();
        return list;
    }

    /**
     * 重置密码
     */
    @RequestMapping("/resetPwd/{id}")
    @ResponseBody
    public DataView resetPwd(@PathVariable("id")Integer id){
        User user=new User();
        user.setId(id);
        user.setPassword("123456");
        userService.updateById(user);
        DataView dataView=new DataView();
        dataView.setMsg("用户重置密码成功！");
        dataView.setCode(200);
        return dataView;
    }



    /**
     * 修改密码
     */
    @RequestMapping("/changePassword")
    @ResponseBody
    public DataView changePassword(User user,String newPwdOne,String newPwdTwo){
        //1.查询数据库旧密码对不对
        User byId = userService.getById(user.getId());
        if (StringUtils.equals(byId.getPassword(), user.getPassword())
              && StringUtils.equals(newPwdOne,newPwdTwo)){
            user.setPassword(newPwdOne);
            userService.updateById(user);
            DataView dataView=new DataView();
            dataView.setMsg("用户重置密码成功！");
            dataView.setCode(200);
            return dataView;
        }
        DataView dataView=new DataView();
        dataView.setMsg("用户重置密码失败！请检查输入！");
        dataView.setCode(500);
        return dataView;
    }



    /**
     * 点击分配时 初始化用户角色
     * 打开分配角色的弹出层
     * 根据ID查询所拥有的角色
     */
    @RequestMapping("/initRoleByUserId")
    @ResponseBody
    public DataView initRoleByUserId(Integer id){
        //1.查询所有角色
        List<Map<String ,Object>>listMaps=roleService.listMaps();
        //2.查询当前登录用户所拥有的角色
        List<Integer> currentUserRoleIds=roleService.queryUserRoleById(id);
        //3.让前端变为选中状态
        for (Map<String ,Object>map:listMaps) {
            Boolean LAY_CHECKED=false;
            Integer roleId=(Integer) map.get("id");
            for (Integer rid:currentUserRoleIds) {
                if (rid.equals(roleId)){
                    LAY_CHECKED=true;
                    break;
                }
            }
            map.put("LAY_CHECKED",LAY_CHECKED);
        }
        return new DataView(Long.valueOf(listMaps.size()),listMaps);
    }


    /**
     * 保护角色与角色之间的关系 1：m
     * 先删除再保存关系
     */
    @RequestMapping("/saveUserRole")
    @ResponseBody
    public DataView saveUserRole(Integer uid,Integer[] ids){
        userService.saveUserRole(uid,ids);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("用户的角色分配成功！");
        return dataView;
    }



}
