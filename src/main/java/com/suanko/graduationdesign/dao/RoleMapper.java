package com.suanko.graduationdesign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suanko.graduationdesign.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    @Select("select mid from role_menu where rid = #{roleId}")
    List<Integer> queryMidByRid(Integer roleId);

    //1.分配菜单栏之前删除所有rid数据
    @Delete("delete from role_menu where rid=#{rid}")
    void deleteRoleByRid(Integer rid);

    //2.保存分配 角色 与 菜单 的关系
    @Insert("insert into role_menu(rid,mid) values (#{rid},#{mid})")
    void saveRoleMenu(Integer rid, Integer mid);

    //根据用户id查询所有的角色
    @Select("select rid from user_role where uid=#{id}")
    List<Integer> queryUserRoleById(Integer id);

    //1.先删除之前的用户与角色关系
    @Delete("delete from user_role where uid=#{uid}")
    void deleteRoleUserByUid(Integer uid);

    //2.保存分配的用户与角色之间的关系
    @Insert("insert into user_role (uid,rid) values (#{uid},#{rid})")
    void saveUserRole(Integer uid, Integer rid);
}
