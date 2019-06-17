package com.am.rbacsystem.realm;

import com.am.rbacsystem.constant.SysConstant;
import com.am.rbacsystem.pojo.User;
import com.am.rbacsystem.service.AuthService;
import com.am.rbacsystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user=(User) principals.getPrimaryPrincipal();
        //查询权限
        List<String> perms=authService.findPermsByUserName(user.getUserName());
        Set set=new HashSet(perms);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
       String userName=(String) token.getPrincipal();
       User user=userService.checkUser(userName);
       if (user==null){
           throw new UnknownAccountException("用户不存在");
       }
        Session session= SecurityUtils.getSubject().getSession();
        session.setAttribute(SysConstant.CURRENT_USER,user);
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),this.getName());
        return info;
    }
}
