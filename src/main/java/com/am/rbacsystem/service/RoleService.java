package com.am.rbacsystem.service;

import com.am.rbacsystem.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> findRoles();

    List<Role> findRoleByPage(Integer currentPage, Integer pageSize, String roleName);

    Role checkRole(String roleName);

    Integer addRole(Role role);

    Role findRoleByRoleName(String roleName);

    int updateRole(Role role);

    int deleteRoles(Integer[] rolesId);

    Role findRoleByRoleId(Integer roleId);

    int updateAuthIds(Role role);
}
