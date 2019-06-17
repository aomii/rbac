package com.am.rbacsystem.service.impl;

import com.am.rbacsystem.dao.RoleMapper;
import com.am.rbacsystem.pojo.Role;
import com.am.rbacsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRoles() {
        return roleMapper.findRoles();
    }

    @Override
    public List<Role> findRoleByPage(Integer currentPage, Integer pageSize, String roleName) {
        return roleMapper.findRoleByPage(currentPage,pageSize,roleName);
    }

    @Override
    public Role checkRole(String roleName) {
        return roleMapper.checkRole(roleName);
    }

    @Override
    public Integer addRole(Role role) {
        return roleMapper.insertSelective(role);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleMapper.checkRole(roleName);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int deleteRoles(Integer[] rolesId) {
        return roleMapper.deleteRoles(rolesId);
    }

    @Override
    public Role findRoleByRoleId(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int updateAuthIds(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }
}
