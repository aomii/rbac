package com.am.rbacsystem.controller;

import com.am.rbacsystem.common.TreeNode;
import com.am.rbacsystem.constant.SysConstant;
import com.am.rbacsystem.pojo.Auth;
import com.am.rbacsystem.pojo.User;
import com.am.rbacsystem.service.AuthService;
import com.am.rbacsystem.utils.Result;
import com.am.rbacsystem.utils.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/loadTree")

    public List loadTree(@RequestParam(value = "id",required = false,defaultValue = "-1") Integer parentId,
                         HttpSession session){
        System.out.println("节点id"+parentId);
        User user=(User)session.getAttribute(SysConstant.CURRENT_USER);
        List<Auth> list=authService.findAuthByParentId(user,parentId);

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
            nodeList.add(node);
        }
        return  nodeList;
    }

    @RequestMapping("/loadGridTree")
    public List<Auth> loadGridTree(){
        List<Auth> list=authService.findAll();
        return TreeUtils.bulidGridTree(list,-1);

    }

    @RequestMapping("/addAuth")
    public Result addAuth(Auth auth){
        //1.检查节点名称是否存在
        Auth _auth=authService.findAuthByNamePerms(auth);
        if (_auth!=null){
            return  Result.error(500,"该节点名称已存在,不能添加");
        }
        try {
            authService.addAuth(auth);
        } catch (Exception e) {
            return  Result.error(500,"添加失败");
        }
        return Result.success(0,"添加成功");
    }

    @RequestMapping("/updateAuth")
    public Result updateAuth(Auth auth){
        Auth _auth=authService.findAuthByNamePerms(auth);
        if (_auth!=null && _auth.getAuthId()!=auth.getAuthId()){
            return Result.error(500,"该节点名称已存在,不能修改");
        }
        int result=authService.updateAuth(auth);
        if (result>0){
            return Result.success();
        }else{
            return Result.error(500,"更新失败");
        }
    }

    @RequestMapping("/deleteAuth")
    public Result deleteAuth(Integer authId,Integer parentId){
        Auth auth=new Auth();
        auth.setAuthId(authId);
        auth.setParentId(parentId);
        System.out.println(auth);
        List<Auth> list=authService.findChidrenByAuthId(authId);
        if (list.size()>0){
            return  Result.error(500,"还要子节点不能删除");
        }
        try {
            authService.deleteAuth(auth);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.error(500,"删除失败");
        }

    }
}
