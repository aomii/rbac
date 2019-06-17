package com.am.rbacsystem.interceptor;

import com.am.rbacsystem.constant.SysConstant;
import com.am.rbacsystem.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri=request.getRequestURI();
        if (uri.endsWith("login.html") || uri.endsWith("doLogin") ||uri.endsWith("login")
        ||uri.endsWith(".js")||uri.endsWith(".css")||uri.endsWith("jpg")||uri.endsWith("png")
                ||uri.endsWith("/druid/*")){
            return  true;
        }
        HttpSession session=request.getSession();
        User _user= (User) session.getAttribute(SysConstant.CURRENT_USER);
        if (_user==null){
            response.sendRedirect("/login.html");
            return false;
        }
        return true;
    }
}
