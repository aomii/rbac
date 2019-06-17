package com.am.rbacsystem.controller;

import com.am.rbacsystem.common.TreeNode;
import com.am.rbacsystem.pojo.Auth;
import com.am.rbacsystem.pojo.Role;
import com.am.rbacsystem.service.AuthService;
import com.am.rbacsystem.service.RoleService;
import com.am.rbacsystem.utils.Result;
import com.am.rbacsystem.utils.TreeUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/list")
    @ResponseBody
    public List<Role> list(){
        List<Role> list=roleService.findRoles();
        return  list;
    }

    @RequestMapping("/manager")
    public String manager(){
        return  "role_manager";
    }

    @RequestMapping("/listPage")
    @ResponseBody
    public Map<String,Object> list(@RequestParam(value = "page",defaultValue = "1",required = false)Integer currentPage,
                                   @RequestParam(value = "rows",defaultValue = "5",required = false)Integer pageSize,String roleName){
        List<Role> list=roleService.findRoleByPage(currentPage,pageSize,roleName);
        PageInfo<Role> pageInfo=new PageInfo<>(list);
        Map<String,Object> result=new HashMap<>();
        result.put("total",pageInfo.getTotal());
        result.put("rows",pageInfo.getList());
        return  result;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(Role role){
        Role _role=roleService.checkRole(role.getRoleName());
        if (_role!=null){
            return  Result.error(500,"该角色已经存在");
        }
        Integer result=roleService.addRole(role);
        if (result>0){
            return  Result.success();
        }else {
            return Result.error(500,"角色添加失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(Role role){
        Role _role=roleService.findRoleByRoleName(role.getRoleName());
        if (_role!=null){
            if(!_role.getRoleId().equals(role.getRoleId())){
                return Result.error(500,"该角色已经存在不能修改");
            }
        }
        //修改
        int result = roleService.updateRole(role);
        if(result>0){
            return Result.success();
        }else{
            return Result.error(500,"更新失败");
        }
    }


    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(String roleIds){
        String [] arr=roleIds.split(",");
        Integer[] rolesId=new Integer[arr.length];
        for (int i = 0; i <arr.length ; i++) {
            rolesId[i]=Integer.parseInt(arr[i]);
        }
        int result=roleService.deleteRoles(rolesId);
        if (result>0){
            return Result.success(0,"删除"+result+"条数据成功");
        }else{
            return Result.error(500,"删除数据失败");
        }
    }


    @RequestMapping("/allAuth/{roleId}")
    @ResponseBody
    public List allAuth(@RequestParam(value = "id",required = false,defaultValue = "-1") Integer parentId,
                        @PathVariable(value = "roleId") Integer roleId){
        Role _role=roleService.findRoleByRoleId(roleId);
        String authIds=_role.getAuthIds();
        List<Auth> list=authService.findAllAuth(parentId);

        List<TreeNode> nodeList = new ArrayList();
        for (Auth auth : list) {
            TreeNode node = new TreeNode();
            node.setId(auth.getAuthId());
            node.setText(auth.getAuthName());
            node.setState(auth.getState());
            node.setIconCls(auth.getIconCls());
            if (auth.getAuthPath() != null && !"".equals(auth.getAuthPath())) {
                Map attr = new HashMap();
                attr.put("authPath", auth.getAuthPath());
                node.setAttributes(attr);
            }
            String[] str=authIds.split(",");
            for (String s : str) {
                if (auth.getAuthId().equals(Integer.parseInt(s))){
                    node.setChecked(true);
                }
            }

            nodeList.add(node);
        }
        return  nodeList;
    }

    @RequestMapping("/loadTree/{roleId}")
    @ResponseBody
    public List<TreeNode> loadTree(@PathVariable("roleId")Integer roleId){
        List<Auth> list = authService.findAll();
        String authIds = roleService.findRoleByRoleId(roleId).getAuthIds();

        return TreeUtils.bulidCheckedTree(list,authIds,-1);
    }

    @RequestMapping("/authUpdate")
    @ResponseBody
    public Result authUpdate(String  authIds,Integer roleId){
        Role role=new Role();
        role.setAuthIds(authIds);
        role.setRoleId(roleId);
        System.out.println(role);
        int result=roleService.updateAuthIds(role);
        if (result>0){
            return Result.success(0,"角色授权成功");
        }else{
            return Result.error(500,"角色授权失败");
        }

    }


}
