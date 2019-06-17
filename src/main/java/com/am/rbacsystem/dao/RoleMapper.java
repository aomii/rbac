package com.am.rbacsystem.dao;

import com.am.rbacsystem.pojo.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> findRoles();

    List<Role> findRoleByPage(Integer currentPage, Integer pageSize, String roleName);

    Role checkRole(String roleName);


    int deleteRoles(Integer[] rolesId);
}