package com.am.rbacsystem.controller;

import com.am.rbacsystem.pojo.User;
import com.am.rbacsystem.service.UserService;
import com.am.rbacsystem.utils.Result;
import com.am.rbacsystem.vo.UserVo;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/doLogin")
    @ResponseBody
    public String login(User user){
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUserName(),user.getPassword());
        Subject subject= SecurityUtils.getSubject();
        try {
            subject.login(token);
            return  "success";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return  "fail";
        }


//        User currentUser=userService.findUserByUsername(user);
//        if (currentUser!=null){
//            session.setAttribute(SysConstant.CURRENT_USER,currentUser);
//            return "success";
//        }
//        return "fail";
    }

    @RequestMapping("/manager")
    public String manager(){
        return "user_manager";
    }


    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions(value = {"sys:user:list"})
    public Map<String,Object> list(@RequestParam(value = "page",defaultValue = "1",required = false)Integer currentPage,
                                   @RequestParam(value = "rows",defaultValue = "5",required = false)Integer pageSize, UserVo vo
                                   ){
        List<User> list=userService.findUserByPage(currentPage,pageSize,vo);
        PageInfo<User> pageInfo=new PageInfo<>(list);
        Map<String,Object> result=new HashMap<>();
        result.put("total",pageInfo.getTotal());
        result.put("rows",pageInfo.getList());
        return  result;
    }


    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions(value = {"sys:user:save"})
    public Result add(User user){
        User _user=userService.checkUser(user.getUserName());
        if (_user!=null){
            return  Result.error(500,"该用户名已经存在");
        }
        Integer result=userService.addUser(user);
        if (result>0){
            return  Result.success();
        }else {
            return Result.error(500,"用户添加失败");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions(value={"sys:user:delete"})
    public Result delete(String userIds){
        String [] arr=userIds.split(",");
        Integer[] usersId=new Integer[arr.length];
        for (int i = 0; i <arr.length ; i++) {
            usersId[i]=Integer.parseInt(arr[i]);
        }
        int result=userService.deleteUsers(usersId);
        if (result>0){
            return Result.success(0,"删除"+result+"条数据成功");
        }else{
            return Result.error(500,"删除数据失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions(value = {"sys:user:update"})
    public Result update(User user){
        //修改用户名不能和已有用户名重复
        User _user=userService.findUserByUsername(user);
        if (_user!=null){
            if(!_user.getUserId().equals(user.getUserId())){
                return Result.error(500,"该用户名已经存在不能修改");
            }
        }
        //修改
        int result = userService.updateUser(user);
        if(result>0){
            return Result.success();
        }else{
            return Result.error(500,"更新失败");
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String exceptionHandler(){
        System.out.println("处理异常");
//        return  "forward:/403.html";
        return "403";
    }

}
